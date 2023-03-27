package tech.mosaleh.together.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.mosaleh.together.domain.repository.HomeRepository
import tech.mosaleh.together.domain.repository.LoginRepository
import tech.mosaleh.together.domain.repository.RegistrationRepository
import tech.mosaleh.together.domain.use_cases.*

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModuleModule {
    @Provides
    fun provideConfirmPasswordValidationUseCase(): ConfirmPasswordValidationUseCase {
        return ConfirmPasswordValidationUseCase()
    }

    @Provides
    fun provideEmailValidationUseCase(): EmailValidationUseCase {
        return EmailValidationUseCase()
    }

    @Provides
    fun providePasswordValidationUseCase(): PasswordValidationUseCase {
        return PasswordValidationUseCase()
    }

    @Provides
    fun provideSignInUseCase(repo: LoginRepository): SignInUserUseCase {
        return SignInUserUseCase(repo)
    }

    @Provides
    fun provideRegistrationUseCase(repo: RegistrationRepository): RegisterUserUseCase {
        return RegisterUserUseCase(repo)
    }

    @Provides
    fun provideGetCaseUseCase(homeRepo: HomeRepository): GetCasesUseCase {
        return GetCasesUseCase(homeRepo)
    }
}