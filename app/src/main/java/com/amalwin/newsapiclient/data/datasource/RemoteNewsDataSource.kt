package com.amalwin.newsapiclient.data.datasource

import com.amalwin.newsapiclient.data.model.APIResponse
import retrofit2.Response

interface RemoteNewsDataSource {
    suspend fun getTopHeadLines(
        country: String,
        page: Int
    ): Response<APIResponse>
}