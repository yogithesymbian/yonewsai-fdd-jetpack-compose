package com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yogiveloper.yonewsai.core.util.time.formatPublishedDate
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules.ArticleImage
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules.ArticleImageShape
import com.yogiveloper.yonewsai.ui.atoms.badge.AppBadge
import com.yogiveloper.yonewsai.ui.theme.YoNewsAiTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ArticleDetailHeader(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    id: Int?,
    article: Article
) {
    with(sharedTransitionScope) {
        Column {
            article.urlToImage?.let { imageUrl ->
                ArticleImage(
                    imageUrl = imageUrl,
                    sourceName = null,
                    shape = ArticleImageShape.Sharp,
                    modifier = Modifier
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = "image-$id"),
                            animatedVisibilityScope = animatedContentScope,
                        )
                        .fillMaxWidth()
                        .height(280.dp)
                        .padding(bottom = 12.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                if (!article.sourceName.isNullOrEmpty()) {
                    AppBadge(
                        article.sourceName,
                    )
                }

                Text(
                    text = article.title ?: "",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = "text-$id"),
                            animatedVisibilityScope = animatedContentScope,
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        if (!article.author.isNullOrEmpty()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "By ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = article.author,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        if (!article.publishedAt.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = formatPublishedDate(article.publishedAt),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun NewsDetailHeaderScreenPreview() {
    YoNewsAiTheme {
        SharedTransitionLayout {
            AnimatedContent(targetState = true, label = "NewsDetailHeaderAnimationPreview") { targetState ->
                if(targetState){

                }
                with(this@SharedTransitionLayout) {
                    ArticleDetailHeader(
                        this@SharedTransitionLayout,
                        this@AnimatedContent,
                        id = 1,
                        article = Article(
                            id = 1,
                            title = "Breaking News: Kotlin Compose Revolutionizes Android Development",
                            description = "Jetpack Compose is transforming how developers build Android UIs with its modern, declarative approach that simplifies the development process.",
                            sourceName = "Tech News Daily",
                            urlToImage = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/2GL6CGJ2H276MXKU5XT6XH6IYE.jpg&w=1440",
                            author = "Yogi Arif Widodo",
                            url = "https://example.com/article",
                            publishedAt = "2025-10-02T20:12:15Z",
                            content = "Jetpack Compose has emerged as a game-changer in Android development. The modern toolkit enables developers to build beautiful, responsive UIs with significantly less code than traditional View-based approaches. With its declarative syntax and powerful state management, Compose is quickly becoming the preferred choice for Android developers worldwide."
                        ),
                    )
                }
            }
        }
    }
}