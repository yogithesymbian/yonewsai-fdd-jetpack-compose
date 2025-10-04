package com.yogiveloper.yonewsai.modules.home_news.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.ArticleDetailBody
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.ArticleDetailHeader
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.ArticleDetailEmptyState
import com.yogiveloper.yonewsai.ui.organisms.AppTopBar
import com.yogiveloper.yonewsai.ui.theme.YoNewsAiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(article: Article?, onBack: () -> Unit) {
    val ctx = LocalContext.current
    Scaffold(
        topBar = {
            AppTopBar(
                 "Article Details",
                 {
                    IconButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, article?.title ?: "")
                                putExtra(Intent.EXTRA_TEXT, "${article?.title}\n\n${article?.url}")
                            }
                            ctx.startActivity(Intent.createChooser(shareIntent, "Share via"))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                onBack = onBack
            )
        }
    ) { padding ->
        if (article == null) {
            ArticleDetailEmptyState(modifier = Modifier.padding(padding))
        } else {
            ArticleDetailContent(
                article = article,
                modifier = Modifier.padding(padding),
                onOpenOriginal = {
                    article.url?.let { url ->
                        ctx.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }
                }
            )
        }
    }
}
@Composable
private fun ArticleDetailContent(
    article: Article,
    modifier: Modifier = Modifier,
    onOpenOriginal: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        article.urlToImage?.let { imageUrl ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.6f)
                                ),
                                startY = 100f
                            )
                        )
                )
            }
        }

        Column {
            ArticleDetailHeader(article)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.outlineVariant
            )

            ArticleDetailBody(article, onOpenOriginal)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsDetailScreenPreview() {
    YoNewsAiTheme {
        NewsDetailScreen(
            article = Article(
                title = "Breaking News: Kotlin Compose Revolutionizes Android Development",
                description = "Jetpack Compose is transforming how developers build Android UIs with its modern, declarative approach that simplifies the development process.",
                sourceName = "Tech News Daily",
                urlToImage = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/2GL6CGJ2H276MXKU5XT6XH6IYE.jpg&w=1440",
                author = "Yogi Arif Widodo",
                url = "https://example.com/article",
                publishedAt = "2025-10-02T20:12:15Z",
                content = "Jetpack Compose has emerged as a game-changer in Android development. The modern toolkit enables developers to build beautiful, responsive UIs with significantly less code than traditional View-based approaches. With its declarative syntax and powerful state management, Compose is quickly becoming the preferred choice for Android developers worldwide."
            ),
            onBack = {}
        )
    }
}