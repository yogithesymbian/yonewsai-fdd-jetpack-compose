package com.yogiveloper.yonewsai.modules.home_news.domain.usecase

import com.yogiveloper.yonewsai.core.util.Resource
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.domain.repository.NewsRepository
import javax.inject.Inject

/*
* @Inject constructor(private val repo: NewsRepository):
* Hilt akan menyediakan NewsRepository yang sudah aku definisikan
* di RepositoryModule.*/
class GetTopHeadlinesUseCase @Inject constructor(
    private val repo: NewsRepository // <-- Disediakan oleh Hilt dari RepositoryModule
) {
    // 1. Overloading operator 'invoke'
    suspend operator fun invoke(country: String = "us"): Resource<List<Article>> {
        return try {
            // 2. Panggil Repository // fungsi getTopHeadlines dari Repository yang sudah di-inject.
            val list = repo.getTopHeadlines(country)
            // 3. Bungkus data sukses dengan Resource.Success
            Resource.Success(list)
        } catch (e: Exception) {
            // 4. Tangkap error dari Repository dan bungkus dengan Resource.Error
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}

/* langkah 3 dan 4
* UseCase bertindak sebagai jembatan yang aman antara
* "dunia data yang liar" (bisa error kapan saja) dan
* "dunia UI yang terstruktur"
* (yang membutuhkan state jelas seperti Loading, Success, Error).*/


/*
* •operator fun invoke:
* •Apa artinya? Ini adalah "sihir" Kotlin.
* Dengan menamai fungsi Anda invoke dan menandainya sebagai operator,
* Anda bisa memanggil objek kelas itu sendiri seolah-olah ia adalah sebuah fungsi.
* •Mengapa ini penting? Ini membuat kode di ViewModel menjadi jauh lebih bersih
* dan fokus pada "apa" yang dilakukan, bukan "bagaimana" melakukannya.*/