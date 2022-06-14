package com.amalwin.newsapiclient.data.datasourceimpl

import com.amalwin.newsapiclient.data.api.NewsAPIService
import com.amalwin.newsapiclient.data.datasource.RemoteNewsDataSource
import com.amalwin.newsapiclient.data.model.APIResponse
import retrofit2.Response

class RemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService,
    private val country: String,
    private val page: Int
) : RemoteNewsDataSource {
    override suspend fun getTopHeadLines(): Response<APIResponse> {
        return newsAPIService.getTopHeadLines(country, page)
    }
}