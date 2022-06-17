package com.amalwin.newsapiclient.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amalwin.newsapiclient.domain.usecases.GetHeadLinesUseCase

class NewsViewModelFactory(
    private val applicationContext: Application,
    private val getHeadLinesUseCase: GetHeadLinesUseCase
) : ViewModelProvider.Factory {
    /**
     * Creates a new instance of the given `Class`.
     *
     * Default implementation throws [UnsupportedOperationException].
     *
     * @param modelClass a `Class` whose instance is requested
     * @return a newly created ViewModel
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(applicationContext, getHeadLinesUseCase) as T
        }
        throw IllegalArgumentException("ViewModel mismatch")
    }
}