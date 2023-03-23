package tech.mosaleh.together.presentation.screens.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.*
import androidx.hilt.navigation.compose.hiltViewModel
import tech.mosaleh.together.presentation.screens.utils.ViewModelEvents

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {

    var passwordVisibility: Boolean by
    remember { mutableStateOf(false) }
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvent.collect { event ->
            when (event) {
                is ViewModelEvents.Success -> {
                    Toast.makeText(
                        context,
                        "Logged in Successfully",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                is ViewModelEvents.Failure -> {
                    Toast.makeText(
                        context,
                        "Login Failed+\n${event.message}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                ViewModelEvents.Loading -> {
                }
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
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
                text = state.emailError,
                color = MaterialTheme.colors.error
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = {
                viewModel.onEvent(LoginUiEvents.PasswordChanged(it))
            },
            isError = state.passwordError != null,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    val icon =
                        if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    Icon(icon, contentDescription = "Toggle password visibility")
                }
            })
        if (state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = MaterialTheme.colors.error
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Forgot Password",
            modifier = Modifier.align(Alignment.Start)
        )

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

                },
        )
    }
}


