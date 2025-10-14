package com.yogiveloper.yonewsai.modules.home_news.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.ArticleCard
import com.yogiveloper.yonewsai.ui.molecules.EmptyState
import com.yogiveloper.yonewsai.ui.molecules.ErrorState
import com.yogiveloper.yonewsai.ui.molecules.LoadingState
import com.yogiveloper.yonewsai.ui.organisms.AppTopBar
import com.yogiveloper.yonewsai.ui.theme.YoNewsAiTheme

/*
* vm: NewsListViewModel = hiltViewModel():
* Di sini, Hilt secara otomatis menyediakan instance NewsListViewModel
* yang terkait dengan siklus hidup layar ini.
* Ini adalah jembatan antara UI dan business logic.*/

// Composable (Stateful on flutter)
@Composable
fun NewsListScreen(
    vm: NewsListViewModel = hiltViewModel(), // <-- 1. Mendapatkan ViewModel dari Hilt <-- Sumber State
    onOpenDetail: (Article) -> Unit // <-- 2. Menerima callback navigasi
) {
    // 3. Mengumpulkan State dari ViewModel // Ia MEMILIKI dan MENGELOLA state-nya sendiri
    val state by vm.uiState.collectAsState() // <-- "State" hidup di sini

    // 4. Mendelegasikan ke Composable
    NewsListContent(
        articles = state.articles,
        isLoading = state.isLoading,
        error = state.error,
        onRefresh = { vm.fetch() },
        onOpenDetail = onOpenDetail
    )
}

// Composable (Stateless on flutter)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListContent(
    // ini Hanya menerima data, tidak tahu dari mana asalnya
    articles: List<Article>,
    isLoading: Boolean,
    error: String?,

    // ini Hanya menerima "perintah", tidak tahu apa isinya
    onRefresh: () -> Unit,
    onOpenDetail: (Article) -> Unit
) {

    // Tidak ada ViewModel, tidak ada .collectAsState()
    // ...
    // Ia hanya menggunakan data yang diberikan untuk menggambar UI

    // 5. Scaffold sebagai Struktur Dasar Layar
    Scaffold(
        topBar = {
            AppTopBar(
                action = {
                    IconButton(onClick = onRefresh) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // 6. Logika Tampilan Inti (when)
            when {
                isLoading -> {
                    LoadingState()
                }
                error != null -> {
                    ErrorState(error = error, onRetry = onRefresh)
                }
                articles.isEmpty() -> {
                    EmptyState()
                }
                else -> {
                    // Gunakan LazyListState untuk mendeteksi posisi scroll
                    val listState = rememberLazyListState()

                    // 7. LazyColumn untuk Daftar yang Efisien
                    LazyColumn(
                        state = listState, // <-- Berikan state ke LazyColumn
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(articles, key = { it.url ?: it.title ?: "" }) { article ->
                            ArticleCard(
                                article = article,
                                onClick = { onOpenDetail(article) }
                            )
                        }
                    }

                    // Logika untuk memicu pemanggilan halaman berikutnya
//                    val isScrolledToEnd = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
//                    if (isScrolledToEnd && !isLoadingNextPage) {
//                        // Panggil onLoadNextPage saat pengguna scroll ke paling akhir
//                        onLoadNextPage()
//                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    YoNewsAiTheme {
        ArticleCard(
            article = Article(
                title = "Breaking News: Kotlin Compose Rocks 🚀",
                description = "Compose simplifies Android UI development with a declarative approach that makes building beautiful UIs easier than ever.",
                sourceName = "OpenAI News",
                urlToImage = "https://abcnews.go.com/US/large-fire-erupts-chevron-refinery-southern-california/story?id\\\\\\\\u003d126171692",
                author = "Yogi Arif Widodo",
                url = "https://www.washingtonpost.com/politics/2025/10/02/trump-fda-abortion-pill/",
                publishedAt = "2025-10-02T20:12:15Z",
                content = "The FDA has little latitude to reject generic versions of approved drugs."
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsListContentPreview() {
    YoNewsAiTheme {
        NewsListContent(
            articles = emptyList(),
            isLoading = false,
            error = null,
            onRefresh = { },
            onOpenDetail = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingStatePreview() {
    YoNewsAiTheme {
        LoadingState()
    }
}

