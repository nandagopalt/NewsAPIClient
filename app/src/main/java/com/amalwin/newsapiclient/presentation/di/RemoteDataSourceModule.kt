package com.amalwin.newsapiclient.presentation.di

import com.amalwin.newsapiclient.data.api.NewsAPIService
import com.amalwin.newsapiclient.data.datasource.RemoteNewsDataSource
import com.amalwin.newsapiclient.data.datasourceimpl.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule() {
    @Singleton
    @Provides
    fun provideRemoteDataSourceInstance(newsAPIService: NewsAPIService): RemoteNewsDataSource {
        return RemoteDataSourceImpl(newsAPIService)
    }
}