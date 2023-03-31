package tech.mosaleh.together.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.mosaleh.together.data.repository.DetailsRepositoryImpl
import tech.mosaleh.together.data.repository.HomeRepositoryImpl
import tech.mosaleh.together.data.repository.LoginRepositoryImpl
import tech.mosaleh.together.data.repository.RegistrationRepositoryImpl
import tech.mosaleh.together.domain.repository.DetailsRepository
import tech.mosaleh.together.domain.repository.HomeRepository
import tech.mosaleh.together.domain.repository.LoginRepository
import tech.mosaleh.together.domain.repository.RegistrationRepository
import tech.mosaleh.together.domain.utils.AuthService
import tech.mosaleh.together.domain.utils.RemoteDatabaseService

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideLoginRepository(authService: AuthService): LoginRepository {
        return LoginRepositoryImpl(authService)
    }

    @Provides
    fun provideRegistrationRepository(authService: AuthService): RegistrationRepository {
        return RegistrationRepositoryImpl(authService)
    }

    @Provides
    fun provideHomeRepository(databaseService: RemoteDatabaseService): HomeRepository {
        return HomeRepositoryImpl(databaseService)
    }

    @Provides
    fun provideDetailsRepo(databaseService: RemoteDatabaseService): DetailsRepository {
        return DetailsRepositoryImpl(databaseService)
    }
}