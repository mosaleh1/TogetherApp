package tech.mosaleh.together.data.repository

import tech.mosaleh.together.domain.repository.RegistrationRepository
import tech.mosaleh.together.domain.utils.AuthService
import tech.mosaleh.together.domain.utils.AuthState

class RegistrationRepositoryImpl(private val authService: AuthService) : RegistrationRepository {

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthState {
        return authService.register(
            firstName = firstName,
            lastName = lastName, email = email, password = password
        )
    }
}