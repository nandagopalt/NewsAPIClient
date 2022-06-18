package com.amalwin.newsapiclient.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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
        try {
            if (isNetworkAvailable(applicationContext)) {
                newsHeadLines.postValue(Resource.Loading())
                val response = getHeadLinesUseCase.execute(country, page)
                newsHeadLines.postValue(response)
            } else {
                newsHeadLines.postValue(Resource.Error(message = "Internet connection not available !"))
            }
        } catch (exception: Exception) {
            newsHeadLines.postValue(Resource.Error(message = exception.message.toString()))
        }

    }

    fun isNetworkAvailable(context: Context): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return true
            else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return true
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo!!.isConnected) return true
        }
        return false
    }
}