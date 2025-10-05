package com.yogiveloper.yonewsai
import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject

@HiltAndroidApp
class App : Application(), SingletonImageLoader.Factory {
    @Inject
    lateinit var imageLoader: dagger.Lazy<ImageLoader>

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return imageLoader.get()
    }
}