package com.yogiveloper.yonewsai.modules.home_news.data.mapper

import com.yogiveloper.yonewsai.modules.home_news.data.remote.dto.ArticleDto
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article

// 1. Deklarasi sebagai Extension Function
fun ArticleDto.toDomain(): Article =
    // 2. Membuat objek domain 'Article'
    Article(
        sourceName = this.source?.name, // flattening "perataan" / mapper / transformer
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )

/*
* Mapper adalah Koki Persiapan (Prep Cook).
* Ia membersihkan, memotong, dan mengubah bahan mentah itu menjadi
* bahan siap masak (Article) yang rapi dan sesuai resep
* untuk digunakan oleh Koki Utama (ViewModel dan UI).*/