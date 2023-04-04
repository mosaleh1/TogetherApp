package tech.mosaleh.together.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.mosaleh.together.data.data_sources.remote.database.FireStoreClient
import tech.mosaleh.together.data.repository.*
import tech.mosaleh.together.domain.repository.*
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

    @Provides
    fun provideLocationRepository(client: FireStoreClient): LocationRepository {
        return LocationRepositoryImpl(client)
    }
}