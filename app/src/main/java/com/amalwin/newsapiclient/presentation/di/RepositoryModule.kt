package com.amalwin.newsapiclient.presentation.di

import com.amalwin.newsapiclient.data.NewsRepositoryImpl
import com.amalwin.newsapiclient.data.datasource.RemoteNewsDataSource
import com.amalwin.newsapiclient.domain.repositories.NewsRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun providesRepositoryInstance(remoteNewsDataSource: RemoteNewsDataSource): NewsRepository {
        return NewsRepositoryImpl(remoteNewsDataSource)
    }
}