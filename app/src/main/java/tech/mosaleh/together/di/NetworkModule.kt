package tech.mosaleh.together.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.mosaleh.together.data.data_sources.remote.auth.AuthServiceImpl
import tech.mosaleh.together.data.data_sources.remote.database.RemoteDatabaseServiceImpl
import tech.mosaleh.together.domain.utils.AuthService
import tech.mosaleh.together.domain.utils.RemoteDatabaseService

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideAuthService(): AuthService {
        return AuthServiceImpl(FirebaseAuth.getInstance())
    }

    @Provides
    fun provideDatabaseService(): RemoteDatabaseService {
        return RemoteDatabaseServiceImpl(FirebaseDatabase.getInstance())
    }

}