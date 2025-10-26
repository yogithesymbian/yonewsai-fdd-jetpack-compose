package com.yogiveloper.yonewsai.modules.home_news.domain.repository

import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article

interface NewsRepository {
    suspend fun getTopHeadlines(country: String = "us", category: String = "technology"): List<Article>
    suspend fun getArticleById(id: Int): Article
}
