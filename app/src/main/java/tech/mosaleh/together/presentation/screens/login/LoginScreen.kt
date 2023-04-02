package tech.mosaleh.together.presentation.screens.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import tech.mosaleh.together.presentation.components.PasswordField
import tech.mosaleh.together.presentation.screens.utils.Screens
import tech.mosaleh.together.presentation.screens.utils.ValidationEvents

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(), navController: NavHostController
) {
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvent.collect { event ->
            when (event) {
                is ValidationEvents.Success -> {
                    Toast.makeText(
                        context, "Logged in Successfully", Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(
                        route = Screens.Home.route
                    )
                }
                is ValidationEvents.Failure -> {
                    Toast.makeText(
                        context, "Login Failed+\n${event.message}", Toast.LENGTH_LONG
                    ).show()
                }
                ValidationEvents.Loading -> {
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(LoginUiEvents.EmailChanged(it))
                },
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
            )
            if (state.emailError != null) {
                Text(
                    text = state.emailError, color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            PasswordField(
                password = state.password, isError = state.passwordError != null
            ) {
                viewModel.onEvent(LoginUiEvents.PasswordChanged(it))
            }
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError, color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Forgot Password", modifier = Modifier
                .align(Alignment.Start)
                .clickable {

                })

            Button(onClick = {
                viewModel.onEvent(LoginUiEvents.Login)
            }) {
                Text(text = "Login")
            }
            Text(
                text = "Create a new Account",
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable {
                        navController.navigate(
                            route = Screens.Registration.route
                        )
                    },
            )
        }
    }
}


