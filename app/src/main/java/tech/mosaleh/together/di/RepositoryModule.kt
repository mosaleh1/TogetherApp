package tech.mosaleh.together.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.mosaleh.together.data.repository.LoginRepositoryImpl
import tech.mosaleh.together.domain.repository.LoginRepository
import tech.mosaleh.together.domain.utils.AuthService

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideLoginRepository(authService: AuthService): LoginRepository {
        return LoginRepositoryImpl(authService)
    }
}