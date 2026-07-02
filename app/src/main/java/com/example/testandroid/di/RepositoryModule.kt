package com.example.testandroid.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.example.testandroid.cores.managers.GoogleAuthManager
import com.example.testandroid.features.auth.login.data.AuthRepositoryImpl
import com.example.testandroid.features.auth.login.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideCredentialManager(
            @ApplicationContext context: Context
        ): CredentialManager = CredentialManager.create(context)

        @Provides
        @Singleton
        fun provideGoogleAuthManager(
            @ApplicationContext context: Context,
            credentialManager: CredentialManager
        ): GoogleAuthManager = GoogleAuthManager(credentialManager)
    }
}