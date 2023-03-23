package tech.mosaleh.together.presentation.screens.login

sealed class LoginUiEvents {
        data class EmailChanged(val email:String): LoginUiEvents()
        data class PasswordChanged(val password:String): LoginUiEvents()
        object Login: LoginUiEvents()
}