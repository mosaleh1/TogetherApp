package tech.mosaleh.together.presentation.screens.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import tech.mosaleh.together.domain.use_cases.ConfirmPasswordValidationUseCase
import tech.mosaleh.together.domain.use_cases.EmailValidationUseCase
import tech.mosaleh.together.domain.use_cases.PasswordValidationUseCase
import tech.mosaleh.together.domain.use_cases.RegisterUserUseCase
import tech.mosaleh.together.domain.utils.AuthState
import tech.mosaleh.together.presentation.screens.utils.ViewModelEvents
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val emailValidation: EmailValidationUseCase,
    private val passwordValidation: PasswordValidationUseCase,
    private val confirmPasswordValidation: ConfirmPasswordValidationUseCase,
    private val registerUser: RegisterUserUseCase
) : ViewModel() {

    var state by mutableStateOf(RegistrationScreenUiState())

    // events from VM to UI
    private val registrationEventsChannel = Channel<ViewModelEvents>()
    val validationEvent = registrationEventsChannel.receiveAsFlow()

    // events from UI to VM
    fun onEvent(event: RegistrationEvents) {

        when (event) {
            is RegistrationEvents.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationEvents.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationEvents.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = event.confirmedPassword)
            }

            is RegistrationEvents.FirstNameChanged -> {
                state = state.copy(firstName = event.firstName)
            }
            is RegistrationEvents.LastNameChanged -> {
                state = state.copy(lastName = event.secondName)
            }
            is RegistrationEvents.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        // get data
        val emailResult = emailValidation.invoke(state.email)
        val passwordResult = passwordValidation.invoke(state.password)
        val repeatedPasswordResult = confirmPasswordValidation.invoke(
            password = state.password,
            repeatedPassword = state.confirmPassword
        )
        // validate data
        val hasError = listOf(
            emailResult, passwordResult, repeatedPasswordResult
        ).any { !it.successful }
        if (hasError) {
            // submit errors if found
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                confirmPasswordError = repeatedPasswordResult.errorMessage
            )
            // return if there's and error
            return
        }
        //when there's no errors
        // remove errors if there's no errors
        state = state.copy(
            emailError = null,
            passwordError = null,
            confirmPasswordError = null
        )
        viewModelScope.launch {
            //sign in user
            registrationEventsChannel.send(ViewModelEvents.Loading)

            val authState = registerUser(
                firstName = state.firstName,
                lastName = state.lastName,
                email = state.email,
                password = state.password
            )
            //on success send to ui Success State
            viewModelScope.launch {

                when (authState) {
                    is AuthState.Success -> {
                        registrationEventsChannel.send(ViewModelEvents.Success)
                    }
                    is AuthState.Failure -> {
                        registrationEventsChannel.send(ViewModelEvents.Failure(authState.message))
                    }
                }
            }
        }
    }
}