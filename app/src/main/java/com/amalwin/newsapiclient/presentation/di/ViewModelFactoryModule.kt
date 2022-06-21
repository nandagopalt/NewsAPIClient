package com.amalwin.newsapiclient.presentation.di

import android.app.Application
import com.amalwin.newsapiclient.domain.usecases.GetHeadLinesUseCase
import com.amalwin.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {
    @Singleton
    @Provides
    fun providesViewModelFactoryInstance(
        applicationContext: Application,
        getHeadLinesUseCase: GetHeadLinesUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(applicationContext, getHeadLinesUseCase)
    }
}