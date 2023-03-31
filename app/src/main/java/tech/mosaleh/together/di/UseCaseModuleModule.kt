package tech.mosaleh.together.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.mosaleh.together.domain.repository.DetailsRepository
import tech.mosaleh.together.domain.repository.HomeRepository
import tech.mosaleh.together.domain.repository.LoginRepository
import tech.mosaleh.together.domain.repository.RegistrationRepository
import tech.mosaleh.together.domain.use_cases.*

@Module
@InstallIn(ViewModelComponent::class)
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

    @Provides
    fun provideInsertCaseUseCase(repo: DetailsRepository): InsertCaseUseCase {
        return InsertCaseUseCase(repo)
    }

    @Provides
    fun provideGetCompleteUseCase(application: Application): GetCompleteAddressUseCase {
        return GetCompleteAddressUseCase(application = application)
    }

    @Provides
    fun provideUploadImageUseCase(repo: DetailsRepository): UploadCaseImageUseCase {
        return UploadCaseImageUseCase(repo)
    }
}