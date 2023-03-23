package tech.mosaleh.together.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.mosaleh.together.data.data_sources.remote.auth.AuthServiceImpl
import tech.mosaleh.together.domain.utils.AuthService

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideAuthService(): AuthService {
        return AuthServiceImpl(FirebaseAuth.getInstance())
    }
    
}