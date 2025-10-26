package com.yogiveloper.yonewsai.modules.home_news.data.mapper

import com.yogiveloper.yonewsai.modules.home_news.data.remote.dto.ArticleDto
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article

fun ArticleDto.toDomain(id: Int): Article =
    Article(
        id = id,
        sourceName = this.source?.name,
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
