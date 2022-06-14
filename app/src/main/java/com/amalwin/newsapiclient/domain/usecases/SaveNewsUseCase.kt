package com.amalwin.newsapiclient.domain.usecases

import com.amalwin.newsapiclient.data.model.Article
import com.amalwin.newsapiclient.domain.repositories.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.saveNewsArticleToDB(article)
}