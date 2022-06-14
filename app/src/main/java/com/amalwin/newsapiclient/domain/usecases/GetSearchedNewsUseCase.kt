package com.amalwin.newsapiclient.domain.usecases

import com.amalwin.newsapiclient.data.model.APIResponse
import com.amalwin.newsapiclient.data.util.Resource
import com.amalwin.newsapiclient.domain.repositories.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(searchQuery: String): Resource<APIResponse> {
        return newsRepository.searchNewsFromAPI(searchQuery)
    }
}