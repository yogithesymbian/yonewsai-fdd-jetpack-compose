package com.yogiveloper.yonewsai.modules.home_news.domain.usecase

import com.yogiveloper.yonewsai.core.util.Resource
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.domain.repository.NewsRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking // <-- Penting untuk tes coroutine
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock // <-- Pustaka untuk membuat objek palsu (mock)
import org.mockito.kotlin.whenever // <-- Pustaka untuk mengatur perilaku mock

class GetTopHeadlinesUseCaseTest {
    // 3. ARRANGE (Global): Membuat dependensi palsu (mock)
    private val repo: NewsRepository = mock()
    private lateinit var useCase: GetTopHeadlinesUseCase

    // 4. Menyiapkan kondisi sebelum setiap tes
    @Before
    fun setup() {
        useCase = GetTopHeadlinesUseCase(repo)
    }

    @Test
    fun whenRepositoryReturnsArticles_UseCaseReturnsSuccess() {
        // 6. Menjalankan tes di dalam scope Coroutine
        runBlocking {
            // ARRANGE (Lokal): Menyiapkan data dan perilaku mock
            val fake = listOf(
                Article(
                    "Source",
                    "Author",
                    "Title",
                    "Desc",
                    "url",
                    "img",
                    "2025-10-01",
                    "content"
                )
            )
            whenever(repo.getTopHeadlines()).thenReturn(fake)

            // ACT: Menjalankan fungsi yang diuji
            val res = useCase()// Memanggil UseCase (ingat operator 'invoke'?)

            // ASSERT: Memverifikasi hasil
            assertTrue(res is Resource.Success) // Pastikan hasilnya adalah Success
            val data = (res as Resource.Success).data // Ambil datanya
            assertEquals(1, data.size) // Pastikan jumlah datanya benar
            assertEquals("Title", data[0].title) // Pastikan isi datanya benar
        }
    }
}