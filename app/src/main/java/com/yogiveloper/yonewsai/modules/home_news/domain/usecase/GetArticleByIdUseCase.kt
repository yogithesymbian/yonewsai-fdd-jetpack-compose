package com.yogiveloper.yonewsai.modules.home_news.domain.usecase

import com.yogiveloper.yonewsai.core.util.Resource
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.domain.repository.NewsRepository
import javax.inject.Inject

class GetArticleByIdUseCase @Inject constructor(
    private val repo: NewsRepository
) {
    suspend operator fun invoke(id: Int) : Resource<Article> {
        return try {
            val article = repo.getArticleById(id)
            Resource.Success(article)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}
