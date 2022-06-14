package com.amalwin.newsapiclient.domain.usecases

import com.amalwin.newsapiclient.data.model.Article
import com.amalwin.newsapiclient.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNewsFromDB()
    }
}