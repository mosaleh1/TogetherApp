package tech.mosaleh.together.presentation.screens.registration

import android.widget.Toast
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import tech.mosaleh.together.presentation.components.CommonTextField
import tech.mosaleh.together.presentation.components.ConditionalProgressBar
import tech.mosaleh.together.presentation.components.ErrorDialog
import tech.mosaleh.together.presentation.components.PasswordField
import tech.mosaleh.together.presentation.screens.utils.Screens
import tech.mosaleh.together.presentation.screens.utils.ValidationEvents

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    var error by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var isDoneSuccessfully by remember {
        mutableStateOf(false)
    }
    var isErrorShown by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = context) {
        viewModel.validationEvent.collect { event ->
            when (event) {
                is ValidationEvents.Success -> {
                    Toast.makeText(
                        context,
                        "Registered Successfully",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    isLoading = false
                    isDoneSuccessfully = true
                }
                is ValidationEvents.Failure -> {
                    Toast.makeText(
                        context,
                        "Registration Failed+\n${event.message}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    error = event.message
                    isLoading = false
                    isErrorShown = true
                }
                ValidationEvents.Loading -> {
                    isLoading = true
                }
            }
        }
    }
    if (isLoading) {
        ConditionalProgressBar {

        }
    }
    if (error.isNotBlank()) {
        ErrorDialog(isShown = isErrorShown, errorMessage = error) {
            isErrorShown = false
        }
    }
    if (isDoneSuccessfully) {
        navController.navigate(
            route = Screens.Home.route
        )
    }
    val state = viewModel.state
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = scrollState
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth()) {
            CommonTextField(
                label = "First Name",
                value = state.firstName,
                isError = state.firstNameError != null,
                error = state.firstNameError,
                onChanged = {
                    viewModel.onEvent(
                        RegistrationEvents.FirstNameChanged(it)
                    )
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            CommonTextField(
                label = "Last Name",
                value = state.lastName,
                isError = state.lastNameError != null,
                error = state.lastNameError,
                onChanged = {
                    viewModel.onEvent(
                        RegistrationEvents.LastNameChanged(it)
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        CommonTextField(
            label = "Email",
            value = state.email,
            isError = state.emailError != null,
            error = state.emailError,
            onChanged = {
                viewModel.onEvent(
                    RegistrationEvents.EmailChanged(it.trim())
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        //
        Spacer(modifier = Modifier.height(10.dp))
        PasswordField(modifier = Modifier.fillMaxWidth(),
            label = "Password",
            password = state.password,
            isError = state.passwordError != null,
            onChanged = {
                viewModel.onEvent(RegistrationEvents.PasswordChanged(it))
            })
        Spacer(modifier = Modifier.height(10.dp))
        PasswordField(modifier = Modifier.fillMaxWidth(),
            label = "Confirm Password",
            password = state.confirmPassword,
            isError = state.confirmPasswordError != null,
            onChanged = {
                viewModel.onEvent(RegistrationEvents.ConfirmPasswordChanged(it))
            })
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            viewModel.onEvent(RegistrationEvents.Submit)
        }) {
            Text(text = "Register")
        }
    }
}






