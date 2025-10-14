package com.yogiveloper.yonewsai
import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject

/*  memberitahu Hilt, "Mulai dari sini, aktifkan dependency injection untuk seluruh aplikasi."
    Anotasi ini memicu pembuatan dependency graph (peta dependensi)
    untuk seluruh aplikasi Anda. Setiap aplikasi yang menggunakan Hilt
    wajib memiliki satu kelas Application dengan anotasi ini.

    mendeklarasikan kelas App sebagai kelas Application kustom.
    register at AndroidManifest.xml
    */
@HiltAndroidApp
class App : Application(), SingletonImageLoader.Factory {

    /* field injection dari Hilt.
    * please sediakan (@Inject) sebuah objek ImageLoader nanti (lateinit).
    *
    * hanya akan membuatnya saat pertama kali benar-benar dibutuhkan (yaitu saat .get() dipanggil).
    * Ini bisa mempercepat waktu startup aplikasi.
    * */
    @Inject
    lateinit var imageLoader: dagger.Lazy<ImageLoader>

    /*
    * Setiap kali Coil membutuhkan ImageLoader di mana pun di aplikasi
    * (misalnya di dalam SubcomposeAsyncImage), ia akan memanggil fungsi ini.
    * Anda kemudian mengembalikan ImageLoader yang sudah disediakan oleh Hilt.
    * Ini memastikan seluruh aplikasi menggunakan satu instance ImageLoader yang sama,
    * yang dikonfigurasi secara terpusat (misalnya dengan OkHttpClient kustom, cache, dll.
    * yang Anda definisikan di Hilt Module).*/
    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return imageLoader.get()
    }
}