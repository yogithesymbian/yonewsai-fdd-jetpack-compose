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

    /**
     * simple cache
     * because limitation from news api
     * there is no api to hit detail */
    private val articleCache = mutableMapOf<String, Article>()

    override suspend fun getTopHeadlines(country: String, category: String): List<Article> {
        try {
            val resp = api.getTopHeadlines(country = country, category= category)
            if (resp.status == "ok") {
                articleCache.clear()
                return resp.articles.mapIndexed { index, it ->
                    val uniqueID = it.url
                    if(uniqueID !== null){
                        articleCache[uniqueID] = it.toDomain(index)
                    }
                    it.toDomain(index)
                }
            } else {
                Log.e("NewsRepositoryImpl", "API returned an error status: ${resp.status}")
                throw Exception("NewsAPI error: ${resp.status}")
            }
        } catch (e: Exception){
            Log.e("NewsRepositoryImpl", "Failed to fetch headlines", e)
            throw e
        }
    }

    override suspend fun getArticleById(id: Int): Article {
        try {
            return articleCache.values.find { it.id == id } ?: throw Exception("Article not found")
        } catch (e: Exception) {
            throw e
        }
    }
}
