package com.amalwin.newsapiclient.data

import com.amalwin.newsapiclient.data.datasource.RemoteNewsDataSource
import com.amalwin.newsapiclient.data.model.APIResponse
import com.amalwin.newsapiclient.data.model.Article
import com.amalwin.newsapiclient.data.util.Resource
import com.amalwin.newsapiclient.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(private val remoteNewsDataSource: RemoteNewsDataSource) : NewsRepository {
    override suspend fun getNewsHeadLinesFromAPI(): Resource<APIResponse> {
       return resourceToResponse(remoteNewsDataSource.getTopHeadLines())
    }

    private fun resourceToResponse(response: Response<APIResponse>): Resource<APIResponse> {
        if(response.isSuccessful) {
            response.body().let { result ->
                 return result.let { Resource.Success(result!!) }
            }
        }
        return Resource.Error(response.body(),response.message())
    }

    override suspend fun searchNewsFromAPI(searchQuery: String): Resource<APIResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNewsArticleToDB(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNewsArticleFromDB(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNewsFromDB(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
}