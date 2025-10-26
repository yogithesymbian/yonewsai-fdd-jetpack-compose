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
            // Use require() to validate the response status, which throws an exception if false.
            require(resp.status == "ok") {
                "API returned an error status: ${resp.status}"
            }

            val domainArticles = resp.articles.mapIndexed { index, articleDto ->
                articleDto.toDomain(index)
            }

            /**
             * Clear the cache and then populate it with the new articles.
             * articleCache.putAll(...) is then used to efficiently
             * add all the new articles to the cache in one go.
             * */
            articleCache.clear()
            articleCache.putAll(domainArticles.associateBy { it.id.toString() })

            return domainArticles
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
