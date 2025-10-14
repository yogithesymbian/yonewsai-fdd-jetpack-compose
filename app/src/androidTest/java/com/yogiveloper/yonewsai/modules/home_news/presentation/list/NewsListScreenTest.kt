package com.yogiveloper.yonewsai.modules.home_news.presentation.list

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

// 2. Deklarasi Kelas Test
class NewsListScreenTest {

    // 3. Menyiapkan Compose Test Rule
    /*
    * JUnit Rule yang menyediakan lingkungan untuk menjalankan
    * dan berinteraksi dengan Composable.
    * rule ini memberikan akses ke dua hal utama:
    * 1.setContent { ... }:
    * Untuk menampilkan Composable yang ingin Anda uji.
    * 2.onNode..., onAllNodes...:
    * Untuk mencari elemen (node) di dalam hierarki UI.
    * 3.perform...:
    * Untuk melakukan aksi pada elemen tersebut
    * (seperti klik, scroll, dll.).
    * */
    @get:Rule
    val rule = createComposeRule()

    // 4. Deklarasi sebuah Test Case
    /*
    * Setiap test case yang baik mengikuti pola
    * Arrange-Act-Assert:
    * */
    @Test
    fun articleTitleIsDisplayed() {

        // 5. ARRANGE: Menyiapkan data dan kondisi
        val items = listOf(
            Article(
                "Source",
                "Author",
                "Test Title", // <- Data yang akan kita cari di UI
                "Desc",
                "url",
                "img",
                "date",
                "content"
            )
        )
//        val items = listOf(testArticle)
//        var clickedArticle: Article? = null // Variabel untuk "menangkap" hasil klik

        // 6. ACT: Menjalankan aksi atau menampilkan UI
        rule.setContent {
            NewsListContent(
                articles = items,
                isLoading = false,
                error = null,
                onRefresh = {},
                onOpenDetail = {}
//                onOpenDetail = { article -> // Saat onOpenDetail dipanggil, simpan artikelnya
//                    clickedArticle = article
//                }
            )
        }

        // 7. ASSERT: Memverifikasi hasil yang diharapkan
        rule.onNodeWithText("Test Title")
            .assertExists()
//            .assertHasClickAction()
//            .assertIsDisplayed() // Pastikan benar-benar terlihat di layar
        // Lakukan klik pada node yang kita inginkan
//        rule.onNodeWithText("Clickable Title").performClick()

        // ASSERT
        // Verifikasi bahwa lambda kita memang dipanggil
//        Assert.assertTrue("onOpenDetail lambda was not called", clickedArticle != null)
        // Verifikasi bahwa artikel yang dikirim adalah artikel yang benar
//        Assert.assertEquals("The wrong article was passed on click", testArticle, clickedArticle)

    }

//    @Test
//    fun refreshButtonIsDisplayed() {
//        // ARRANGE & ACT
//        rule.setContent {        NewsListContent(
//            articles = emptyList(), // Tidak perlu artikel untuk tes ini
//            isLoading = false,
//            error = null,
//            onRefresh = {},
//            onOpenDetail = {}
//        )
//        }
//
//        // ASSERT
//        rule.onNodeWithContentDescription("Refresh")
//            .assertExists()
//            .assertIsDisplayed()
//    }
}
