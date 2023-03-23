package tech.mosaleh.together.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.mosaleh.together.domain.repository.LoginRepository
import tech.mosaleh.together.domain.utils.AuthService
import tech.mosaleh.together.domain.utils.AuthState

class LoginRepositoryImpl(private val authService: AuthService) : LoginRepository {

    override suspend fun signIn(email: String, password: String): AuthState {
        return withContext(Dispatchers.IO) {
            authService.signIn(email, password)
        }
    }

}