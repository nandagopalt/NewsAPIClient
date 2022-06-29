package com.amalwin.newsapiclient.presentation.di

import com.amalwin.newsapiclient.presentation.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Singleton
    @Provides
    fun provideAdapterInstance(): NewsAdapter {
        return NewsAdapter()
    }
}