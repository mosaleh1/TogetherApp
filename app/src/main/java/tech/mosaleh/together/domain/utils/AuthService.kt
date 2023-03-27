package tech.mosaleh.together.domain.utils

interface AuthService {

    suspend fun signIn(email: String, password: String): AuthState

    suspend fun register(firstName: String, lastName: String, email: String, password: String): AuthState

}