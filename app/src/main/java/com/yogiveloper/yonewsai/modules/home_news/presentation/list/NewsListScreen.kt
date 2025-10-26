package com.yogiveloper.yonewsai.modules.home_news.presentation.list

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

@Composable
fun NewsListScreen(
    vm: NewsListViewModel = hiltViewModel(),
    onOpenDetail: (Article) -> Unit
) {
    val state by vm.uiState.collectAsState()
    NewsListContent(
        articles = state.articles,
        isLoading = state.isLoading,
        error = state.error,
        onRefresh = { vm.fetch() },
        onOpenDetail = onOpenDetail
    )
}

private sealed class NewsScreenState {
    object Loading : NewsScreenState()
    object Success : NewsScreenState()
    object Empty : NewsScreenState()
    data class Error(val message: String) : NewsScreenState()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class) // <-- Tambahan OptIn
@Composable
fun NewsListContent(
    articles: List<Article>,
    isLoading: Boolean,
    error: String?,
    onRefresh: () -> Unit,
    onOpenDetail: (Article) -> Unit
) {
    val targetState by remember(isLoading, error, articles.isEmpty()) {
        derivedStateOf {
            when {
                isLoading -> NewsScreenState.Loading
                error != null -> NewsScreenState.Error(error)
                articles.isEmpty() -> NewsScreenState.Empty
                else -> NewsScreenState.Success
            }
        }
    }

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
        Crossfade(
            targetState = targetState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            animationSpec = tween(durationMillis = 500), // 0.5 second animation
            label = "StateCrossfade"
        ) { state ->
            when (state) {
                is NewsScreenState.Loading -> {
                    LoadingState()
                }
                is NewsScreenState.Error -> {
                    ErrorState(error = state.message, onRetry = onRefresh)
                }
                is NewsScreenState.Empty -> {
                    EmptyState()
                }
                is NewsScreenState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(articles, key = { it.url ?: it.title ?: "" }) { article ->
                            ArticleCard(
                                article = article,
                                onClick = { onOpenDetail(article) },
                                modifier = Modifier
                                    .animateItem(
                                        fadeInSpec = tween(400),
                                        fadeOutSpec = tween(300),
                                        placementSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        )

                                    )
                            )
                        }
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsListContentPreview() {
    val dummyListArticle = List(5 ) { index ->
        Article(
            id = 1,
            title = "Breaking News: Kotlin Compose Rocks 🚀",
            description = "Compose simplifies Android UI development with a declarative approach that makes building beautiful UIs easier than ever.",
            sourceName = "OpenAI News",
            urlToImage = "https://abcnews.go.com/US/large-fire-erupts-chevron-refinery-southern-california/story?id\\\\\\\\u003d126171692",
            author = "Yogi Arif Widodo",
            url = "$index",
            publishedAt = "2025-10-02T20:12:15Z",
            content = "The FDA has little latitude to reject generic versions of approved drugs."
        )
    }
    YoNewsAiTheme {
        NewsListContent(
            articles = dummyListArticle,
            isLoading = false,
            error = null,
            onRefresh = { },
            onOpenDetail = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsListContentPreviewEmpty() {
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