package com.yogiveloper.yonewsai.core.di

import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.CachePolicy
import android.content.Context
import coil3.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {
    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(OkHttpNetworkFetcherFactory())
            }
            .networkCachePolicy(CachePolicy.ENABLED)
            .build()
    }
}
