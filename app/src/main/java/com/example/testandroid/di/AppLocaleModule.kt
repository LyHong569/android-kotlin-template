package com.example.testandroid.di

import android.content.Context
import com.example.testandroid.cores.locales.AppLocaleManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppLocaleModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context?): Context? {
        return context
    }

    @Provides
    @Singleton
    fun provideLocaleManager(
        @ApplicationContext context: Context
    ): AppLocaleManager = AppLocaleManager(context)
}