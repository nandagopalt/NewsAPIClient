package com.amalwin.newsapiclient.domain.repositories

import com.amalwin.newsapiclient.data.model.APIResponse
import com.amalwin.newsapiclient.data.model.Article
import com.amalwin.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadLinesFromAPI(): Resource<APIResponse>
    suspend fun searchNewsFromAPI(searchQuery: String): Resource<APIResponse>

    suspend fun saveNewsArticleToDB(article: Article)
    suspend fun deleteNewsArticleFromDB(article: Article)
    fun getSavedNewsFromDB(): Flow<List<Article>>
}