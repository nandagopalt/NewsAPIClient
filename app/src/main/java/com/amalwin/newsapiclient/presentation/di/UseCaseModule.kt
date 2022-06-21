package com.amalwin.newsapiclient.presentation.di

import com.amalwin.newsapiclient.domain.repositories.NewsRepository
import com.amalwin.newsapiclient.domain.usecases.GetHeadLinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideUseCaseInstance(repository: NewsRepository): GetHeadLinesUseCase {
        return GetHeadLinesUseCase(repository)
    }
}