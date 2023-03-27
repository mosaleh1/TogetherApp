package tech.mosaleh.together.domain.use_cases

import tech.mosaleh.together.domain.repository.RegistrationRepository
import tech.mosaleh.together.domain.utils.AuthState

class RegisterUserUseCase(private val repo: RegistrationRepository) {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthState {
        return repo.register(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password
        )
    }
}