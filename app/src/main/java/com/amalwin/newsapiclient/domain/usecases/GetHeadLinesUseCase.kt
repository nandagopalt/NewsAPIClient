package com.amalwin.newsapiclient.domain.usecases

import com.amalwin.newsapiclient.data.model.APIResponse
import com.amalwin.newsapiclient.data.util.Resource
import com.amalwin.newsapiclient.domain.repositories.NewsRepository

class GetHeadLinesUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, page: Int): Resource<APIResponse> {
        return newsRepository.getNewsHeadLinesFromAPI(country, page)
    }
}