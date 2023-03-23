package tech.mosaleh.together.domain.use_cases

import tech.mosaleh.together.domain.repository.LoginRepository
import tech.mosaleh.together.domain.utils.AuthState

class SignInUserUseCase(private val repo: LoginRepository) {

    private lateinit var state: AuthState

    suspend operator fun invoke(email: String, password: String): AuthState {
        return repo.signIn(email, password)
    }
}