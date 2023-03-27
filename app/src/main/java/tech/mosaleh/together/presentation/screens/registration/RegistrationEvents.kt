package tech.mosaleh.together.presentation.screens.registration


sealed class RegistrationEvents {
    data class FirstNameChanged(val firstName: String) : RegistrationEvents()
    data class LastNameChanged(val secondName: String) : RegistrationEvents()
    data class EmailChanged(val email: String) : RegistrationEvents()
    data class PasswordChanged(val password: String) : RegistrationEvents()
    data class ConfirmPasswordChanged(val confirmedPassword: String) : RegistrationEvents()
    object Submit : RegistrationEvents()
}