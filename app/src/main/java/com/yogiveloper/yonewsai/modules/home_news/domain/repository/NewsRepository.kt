package com.yogiveloper.yonewsai.modules.home_news.domain.repository

import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article

interface NewsRepository {
    suspend fun getTopHeadlines(country: String = "us"): List<Article>
}
