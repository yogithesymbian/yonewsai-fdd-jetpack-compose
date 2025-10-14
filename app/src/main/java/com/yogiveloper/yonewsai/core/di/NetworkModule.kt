package com.yogiveloper.yonewsai.core.di

import com.yogiveloper.yonewsai.modules.home_news.data.remote.api.NewsApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import com.yogiveloper.yonewsai.BuildConfig

// 1. Deklarasi sebagai Modul Hilt (analoginya merakit mobil )
/*
* @Module: Memberitahu Hilt bahwa NetworkModule
* adalah sebuah modul yang berisi "resep-resep" (instruksi)
* untuk membuat dependensi
*
* @InstallIn(SingletonComponent::class):
* Memberitahu Hilt bahwa semua resep di dalam modul ini
* akan memiliki siklus hidup aplikasi (singleton).
* Artinya, objek yang dibuat (seperti OkHttpClient atau Retrofit)
* hanya akan dibuat satu kali dan
* akan digunakan kembali di seluruh aplikasi.
* Ini sangat efisien */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // 2. Resep untuk membuat OkHttpClient (analoginya mesinnya )
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val apiKey = BuildConfig.NEWS_API_KEY

        // Membuat Interceptor untuk menambahkan API Key ke setiap request
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val url = original.url.newBuilder()
                .addQueryParameter("apiKey", apiKey)
                .build()
            val req = original.newBuilder().url(url).build()
            chain.proceed(req)
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    // 3. Resep untuk membuat Moshi (analoginya listriknya )
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    // 4. Resep untuk membuat Retrofit (analoginya rangka mobil)
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    // 5. Resep untuk membuat NewsApiService (analoginya ready to drive )
    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)
}
