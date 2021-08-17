package com.example.android.demoproject.di

import com.example.android.demoproject.data.AgricultureApi
import com.example.android.demoproject.data.AgricultureRepository
import com.example.android.demoproject.data.AgricultureRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(agricultureApi: AgricultureApi): AgricultureRepository =
        AgricultureRepositoryImpl(agricultureApi, Dispatchers.IO)
}