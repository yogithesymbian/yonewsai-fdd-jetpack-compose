package com.yogiveloper.yonewsai.modules.home_news.data.repository

import android.util.Log
import com.yogiveloper.yonewsai.modules.home_news.data.mapper.toDomain
import com.yogiveloper.yonewsai.modules.home_news.data.remote.api.NewsApiService
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.domain.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

/*
* @Inject constructor(private val api: NewsApiService):
* Ini memberitahu Hilt cara membuat NewsRepositoryImpl.
* Hilt akan menyediakan NewsApiService yang sudah aku buat
* di NetworkModule.
*
* langkah 3
* langkah krusial dalam Clean Architecture.
* Objek DTO (Data Transfer Object) dari API (resp.articles)
* tidak boleh bocor ke Domain atau UI Layer.
* Fungsi toDomain() mengubah objek data jaringan
* menjadi objek model domain (Article) yang bersih dan
* hanya berisi informasi yang dibutuhkan oleh aplikasi.
* Ini mengisolasi lapisan UI dari perubahan di API.*/
@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApiService // Disediakan oleh Hilt dari NetworkModule
) : NewsRepository { // <-- Memenuhi janji dari interface NewsRepository
    override suspend fun getTopHeadlines(country: String): List<Article> {
        try {
            // 1. call API // Thread utama bebas melakukan hal lain.
            val resp = api.getTopHeadlines(country = country)

            // LANJUT OTOMATIS: Coroutine dilanjutkan di sini saat data dari API sudah tiba.
            // Sekarang, 'resp' berisi data respons dari jaringan.

            // 2. check status response dari API
            if (resp.status == "ok") {
                Log.d("NewsRepositoryImpl", "ass")
                Log.d("NewsRepositoryImpl", "asd ${resp.totalResults}")
                // 3. Transformasi/Mapping
                return resp.articles.map { it.toDomain() }
            } else {
                Log.e("NewsRepositoryImpl", "API returned an error status: ${resp.status}")
                // 4. Tangani jika API mengembalikan status error
                throw Exception("NewsAPI error: ${resp.status}")
            }
        } catch (e: Exception){
            Log.e("NewsRepositoryImpl", "Failed to fetch headlines", e)
            // 5. Tangani error jaringan atau lainya
            throw e // lemparkan lagi agar bisa ditangani oleh UseCase
        }
        /*
        * Langkah 4 & 5 (throw e):
        * Repository tidak menangani state (seperti Resource.Error).
        * Tugasnya hanya mengambil data atau gagal.
        * Jika gagal, ia akan melempar Exception.
        * Tanggung jawab untuk menangkap Exception ini dan
        * mengubahnya menjadi state yang bisa dimengerti
        * UI (Resource.Error) diserahkan ke lapisan di atasnya,
        * yaitu UseCase.*/
    }
}
