package tech.mosaleh.together.domain.utils

interface AuthService {

    suspend fun signIn(email: String, password: String): AuthState

    suspend fun registration(email: String, password: String): AuthState

}