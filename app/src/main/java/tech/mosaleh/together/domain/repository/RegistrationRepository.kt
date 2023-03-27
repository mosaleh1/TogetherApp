package tech.mosaleh.together.domain.repository

import tech.mosaleh.together.domain.utils.AuthState

interface RegistrationRepository {
    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ): AuthState
}