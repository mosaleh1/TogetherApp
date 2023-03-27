package tech.mosaleh.together.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import tech.mosaleh.together.domain.use_cases.EmailValidationUseCase
import tech.mosaleh.together.domain.use_cases.PasswordValidationUseCase
import tech.mosaleh.together.domain.use_cases.SignInUserUseCase
import tech.mosaleh.together.domain.utils.AuthState
import tech.mosaleh.together.presentation.screens.utils.ValidationEvents
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUserUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
) : ViewModel() {


    //states for UI
    var state by mutableStateOf(LoginUiState())

    //channel for sending data to UI
    private val loginEventsChannel = Channel<ValidationEvents>()
    val validationEvent = loginEventsChannel.receiveAsFlow()

    fun onEvent(event: LoginUiEvents) {
        when (event) {
            is LoginUiEvents.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is LoginUiEvents.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            LoginUiEvents.Login -> {
                submitLoginData()
            }
        }
    }

    private fun submitLoginData() {

        // collect data
        val emailResult = emailValidationUseCase.invoke(state.email)
        val passwordResult = passwordValidationUseCase.invoke(state.password)

        // validate data
        val hasError = listOf(
            emailResult, passwordResult
        ).any { !it.successful }

        if (hasError) {
            // submit errors and return from the function
            state = state.copy(
                emailError = emailResult.errorMessage
            )
            state = state.copy(
                passwordError = passwordResult.errorMessage
            )
            return
        }
        // onSuccess
        //clear errors and submit success
        state = state.copy(
            emailError = null,
            passwordError = null
        )
        viewModelScope.launch {
            loginEventsChannel.send(ValidationEvents.Loading)
            val authState = signInUseCase.invoke(
                state.email,
                state.password
            )
            when (authState) {
                is AuthState.Success -> {
                    loginEventsChannel.send(
                        ValidationEvents.Success
                    )
                }
                is AuthState.Failure -> {
                    loginEventsChannel.send(
                        ValidationEvents.Failure(authState.message)
                    )
                }
            }
        }
    }
}
