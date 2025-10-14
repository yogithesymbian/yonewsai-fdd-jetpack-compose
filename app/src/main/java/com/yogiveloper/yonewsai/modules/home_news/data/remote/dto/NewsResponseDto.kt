package com.yogiveloper.yonewsai.modules.home_news.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true) // ksp(libs.moshi.kotlin.codegen) di file build.gradle.kts
data class NewsResponseDto(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class ArticleDto(
    val source: SourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

@JsonClass(generateAdapter = true)
data class SourceDto(val id: String?, val name: String?)

/*
* dibuat nullable ArticleDto (DTO):
* API tidak selalu bisa diandalkan.
* Terkadang, sebuah artikel mungkin tidak memiliki author,
* atau description, atau bahkan urlToImage.
*
* Article (Model Domain):
* "Bersih" dan bisa diandalkan.
* Strukturnya Anda yang tentukan sesuai kebutuhan aplikasi.
* Anda bisa mengubah properti null dari DTO menjadi nilai
* default yang aman (seperti string kosong) di model domain.
* Model domain inilah yang digunakan oleh UseCase dan ViewModel.*/
