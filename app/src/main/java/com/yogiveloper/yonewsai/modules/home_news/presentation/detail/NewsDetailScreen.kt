package com.yogiveloper.yonewsai.modules.home_news.presentation.detail

import android.content.Intent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.ArticleDetailBody
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.ArticleDetailHeader
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.ArticleDetailEmptyState
import androidx.core.net.toUri
import com.yogiveloper.yonewsai.ui.organisms.AppTopBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun NewsDetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    id: Int?,
    article: Article?,
    onBack: () -> Unit
) {
    val ctx = LocalContext.current

    // ✅ Remember last valid article, automatically saved because it's Parcelable
    val lastValidArticle = rememberSaveable { mutableStateOf<Article?>(article) }

    // ✅ Update retained article only when a new one is available (not null)
    LaunchedEffect(article) {
        if (article != null) {
            lastValidArticle.value = article
        }
    }

    val articleToShow = lastValidArticle.value

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Article Details",
                action = {
                    if (articleToShow != null) {
                        IconButton(
                            onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, articleToShow.title ?: "")
                                    putExtra(Intent.EXTRA_TEXT, "${articleToShow.title}\n\n${articleToShow.url}")
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
                    }
                },
                onBack = onBack
            )
        }
    ) { padding ->
        if (articleToShow == null) {
            // ✅ Show empty state only if the screen never received a valid article
            ArticleDetailEmptyState(modifier = Modifier.padding(padding))
        } else {
            ArticleDetailContent(
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                id = id,
                article = articleToShow,
                modifier = Modifier.padding(padding),
                onOpenOriginal = {
                    articleToShow.url?.let { url ->
                        ctx.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ArticleDetailContent(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    id: Int?,
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
        with(sharedTransitionScope) {
            ArticleDetailHeader(
                sharedTransitionScope,
                animatedContentScope,
                id,
                article
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.outlineVariant
        )

        ArticleDetailBody(article, onOpenOriginal)
    }
}

//@OptIn(ExperimentalSharedTransitionApi::class)
//@Preview(showBackground = true)
//@Composable
//fun NewsDetailScreenPreview() {
//    YoNewsAiTheme {
//        NewsDetailScreen(
//            this@SharedTransitionLayout,
//            this@composeable,
//            id = 1,
//            article = Article(
//                id = 1,
//                title = "Breaking News: Kotlin Compose Revolutionizes Android Development",
//                description = "Jetpack Compose is transforming how developers build Android UIs with its modern, declarative approach that simplifies the development process.",
//                sourceName = "Tech News Daily",
//                urlToImage = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/2GL6CGJ2H276MXKU5XT6XH6IYE.jpg&w=1440",
//                author = "Yogi Arif Widodo",
//                url = "https://example.com/article",
//                publishedAt = "2025-10-02T20:12:15Z",
//                content = "Jetpack Compose has emerged as a game-changer in Android development. The modern toolkit enables developers to build beautiful, responsive UIs with significantly less code than traditional View-based approaches. With its declarative syntax and powerful state management, Compose is quickly becoming the preferred choice for Android developers worldwide."
//            ),
//            onBack = {}
//        )
//    }
//}