package tech.mosaleh.together.domain.repository

import tech.mosaleh.together.domain.utils.AuthState

interface LoginRepository {
    suspend fun signIn(email: String, password: String): AuthState
}