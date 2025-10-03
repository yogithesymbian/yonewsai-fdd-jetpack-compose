package com.yogiveloper.yonewsai.modules.home_news.presentation.list


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article

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
                { Text( "YoNewsAi - Top Headlines") }
            )
        }) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
                } else if (error != null) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error: $error")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onRefresh) { Text("Retry") }
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(articles) { article ->
                            ArticleCard(article = article, onClick = { onOpenDetail(article) })
                        }
                    }
                }
            }
    }
}

@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            val imageUrl = article.urlToImage
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(model = imageUrl, contentDescription = null, modifier = Modifier.size(96.dp))
            }
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(article.title ?: "", style = MaterialTheme.typography.titleMedium, maxLines = 2)
                Text(article.description ?: "", style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                Text(article.sourceName ?: "", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    ArticleCard(
        article = Article(
            title = "Breaking News: Kotlin Compose Rocks 🚀",
            description = "Compose simplifies Android UI development with a declarative approach.",
            sourceName = "OpenAI News",
            urlToImage = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/2GL6CGJ2H276MXKU5XT6XH6IYE.jpg&w=1440",
            author = "Yogi Arif Widodo",
            url = "https://www.washingtonpost.com/politics/2025/10/02/trump-fda-abortion-pill/",
            publishedAt = "2025-10-02T20:12:15Z",
            content = "The FDA has little latitude to reject generic versions of approved drugs, but that hasn’t stopped an outrcry from abortion opponents."
        ),
        onClick = {}
    )
}