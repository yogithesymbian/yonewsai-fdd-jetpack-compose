package com.yogiveloper.yonewsai.modules.home_news.domain.repository

import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article

/*
* sebuah kontrak atau janji.
* Ia tidak melakukan apa-apa, hanya mendefinisikan
* "apa yang bisa dilakukan".
* Dalam hal ini, ia berjanji,
* "Siapa pun yang mengimplementasikan saya,
* HARUS menyediakan fungsi getTopHeadlines.
*
* Mengapa ini penting?:
* Domain Layer (Use Case) dan Presentation Layer (ViewModel)
* hanya akan tahu tentang NewsRepository (kontraknya),
* bukan NewsRepositoryImpl (cara kerjanya).
* Ini memungkinkan Anda di masa depan untuk dengan mudah
* mengganti implementasi—misalnya,
* mengambil data dari database lokal jika offline—tanpa
* mengubah satu baris kode pun di ViewModel atau UseCase."*/
interface NewsRepository {
    suspend fun getTopHeadlines(country: String = "us"): List<Article>
}


/*
*  inti dari pemrograman asinkron (asynchronous) di Kotlin.
* Keyword suspend
* adalah penanda yang memberitahu Kotlin:
* "Hei, fungsi ini mungkin butuh waktu lama untuk selesai,
* dan ia bisa dijeda (pause)."
* Dalam kasus ini, getTopHeadlines perlu mengambil data dari internet (API),
* yang bisa lambat.
*
* Analogi Sederhana:
* Sebagai Koki, Anda menyuruh asisten (NewsRepository)
* untuk mengambil daging dari gudang (suspend fun).
* Anda tidak berdiri diam menunggu di depan pintu gudang.
* Sebaliknya, Anda bisa mulai memotong sayuran lain (menjaga UI tetap responsif).
* Saat asisten kembali dengan daging,
* Anda melanjutkan pekerjaan memasak daging tersebut.
* */