package tech.mosaleh.together.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.mosaleh.together.R

//state.passwordError != null
@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    label: String = "Password",
    password: String,
    isError: Boolean,
    onChanged: (String) -> Unit
) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    OutlinedTextField(value = password,
        onValueChange = {
            onChanged(it)
        },
        isError = isError,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(text = label)
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
}


@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isError: Boolean,
    error: String?,
    keyboardType: KeyboardType = KeyboardType.Text,
    onChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChanged,
        isError = isError,
        modifier = modifier,
        placeholder = {
            Text(text = label)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
    )
    if (error != null) {
        Text(
            text = error, color = MaterialTheme.colors.error
        )
    }
}

@Composable
fun ErrorScreen(message: String) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image = painterResource(id = R.drawable.empty)
        val errorImage = "Not Found yet"
        Image(painter = image, contentDescription = errorImage)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message, fontSize = 16.sp, color = Color.Red)
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxSize()
            .background(Color(0x00, 0x00, 0x00, 0x51))
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Loading()
}








