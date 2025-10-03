package com.yogiveloper.yonewsai.modules.home_news.data.repository

import android.util.Log
import com.yogiveloper.yonewsai.modules.home_news.data.mapper.toDomain
import com.yogiveloper.yonewsai.modules.home_news.data.remote.api.NewsApiService
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.domain.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApiService
) : NewsRepository {
    override suspend fun getTopHeadlines(country: String): List<Article> {
        try {
            val resp = api.getTopHeadlines(country = country)
            if (resp.status == "ok") {
                Log.d("NewsRepositoryImpl", "ass")
                Log.d("NewsRepositoryImpl", "asd ${resp.totalResults}")
                return resp.articles.map { it.toDomain() }
            } else {
                Log.e("NewsRepositoryImpl", "API returned an error status: ${resp.status}")
                throw Exception("NewsAPI error: ${resp.status}")
            }
        } catch (e: Exception){
            Log.e("NewsRepositoryImpl", "Failed to fetch headlines", e)
            throw e
        }
    }
}
