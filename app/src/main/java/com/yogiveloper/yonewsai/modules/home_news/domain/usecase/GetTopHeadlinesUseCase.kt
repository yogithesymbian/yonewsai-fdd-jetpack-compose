package com.yogiveloper.yonewsai.modules.home_news.domain.usecase

import com.yogiveloper.yonewsai.core.util.Resource
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.domain.repository.NewsRepository
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val repo: NewsRepository
) {
    suspend operator fun invoke(country: String = "us", category: String = "technology"): Resource<List<Article>> {
        return try {
            val list = repo.getTopHeadlines(country, category)
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}
