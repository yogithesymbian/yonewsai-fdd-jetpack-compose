package com.yogiveloper.yonewsai.modules.home_news.data.remote.api

import com.yogiveloper.yonewsai.modules.home_news.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 3,
        @Query("category") category: String = "technology",
    ): NewsResponseDto
}
