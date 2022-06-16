package com.amalwin.newsapiclient.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalwin.newsapiclient.data.model.APIResponse
import com.amalwin.newsapiclient.data.util.Resource
import com.amalwin.newsapiclient.domain.usecases.GetHeadLinesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val applicationContext: Application,
    private val getHeadLinesUseCase: GetHeadLinesUseCase
) :
    AndroidViewModel(applicationContext) {
    private val newsHeadLines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getTopHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadLines.postValue(Resource.Loading())
        val response = getHeadLinesUseCase.execute(country, page)
        newsHeadLines.postValue(response)
    }
}