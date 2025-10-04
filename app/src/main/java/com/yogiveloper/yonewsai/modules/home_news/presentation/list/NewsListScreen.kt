package com.yogiveloper.yonewsai.modules.home_news.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.ArticleCard
import com.yogiveloper.yonewsai.ui.molecules.LoadingState

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListContent(
    articles: List<Article>,
    isLoading: Boolean,
    error: String?,
    onRefresh: () -> Unit,
    onOpenDetail: (Article) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "YoNewsAI",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onRefresh) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
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
                    LazyColumn(
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
                }
            }
        }
    }
}

@Composable
private fun ErrorState(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "😕",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Oops! Something went wrong",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Try Again")
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📰",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No news available",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Check back later for updates",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    MaterialTheme {
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
    MaterialTheme {
        NewsListContent(
            articles = emptyList(),
            isLoading = false,
            error = null,
            onRefresh = { },
            onOpenDetail = { }
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun LoadingStatePreview() {
//    MaterialTheme {
//        LoadingState()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ErrorStatePreview() {
//    MaterialTheme {
//        ErrorState(error = "Network connection failed", onRetry = {})
//    }
//}