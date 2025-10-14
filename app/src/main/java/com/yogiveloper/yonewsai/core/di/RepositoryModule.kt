package com.yogiveloper.yonewsai.core.di

import com.yogiveloper.yonewsai.modules.home_news.data.remote.api.NewsApiService
import com.yogiveloper.yonewsai.modules.home_news.data.repository.NewsRepositoryImpl
import com.yogiveloper.yonewsai.modules.home_news.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
* kita perlu "sopir" yang tahu cara menggunakannya.
* Itulah peran Repository.
* Repository adalah pola arsitektur yang mengisolasi
* data layer dari domain/UI layer.*/
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // 2. Resep untuk membuat NewsRepository
    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApiService): NewsRepository =
        NewsRepositoryImpl(api)

    /*
    * provideNewsRepository(api: NewsApiService): NewsRepository:
    * Tujuan: Menyediakan implementasi dari NewsRepository.
    * Prinsip Desain yang Kuat:
    * Perhatikan tipe kembaliannya adalah NewsRepository
    * (sebuah interface/abstraksi), tetapi yang Anda buat sebenarnya adalah NewsRepositoryImpl
    * (sebuah implementasi/kelas konkret).
    * Ini disebut Programming to an Interface.
    * ViewModel Anda nanti hanya akan tahu tentang NewsRepository,
    * dan tidak peduli apakah datanya berasal dari API (NewsRepositoryImpl) atau dari database lokal.
    * Ini membuat kode sangat fleksibel dan mudah di-test.
    *
    * Keajaiban Hilt (Lagi): Hilt melihat, "Untuk membuat NewsRepository, saya butuh NewsApiService.
    * Saya sudah punya resepnya di NetworkModule!
    * Saya akan ambil hasilnya dan masukkan ke sini."*/
}