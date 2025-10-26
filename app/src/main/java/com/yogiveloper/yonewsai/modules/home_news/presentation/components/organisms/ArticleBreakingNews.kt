package com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogiveloper.yonewsai.core.util.time.getTimeAgo
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules.ArticleImage
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules.ArticleImageShape
import com.yogiveloper.yonewsai.ui.organisms.AppPagerIndicator
import com.yogiveloper.yonewsai.ui.theme.YoNewsAiTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BreakingNewsSection(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    articles: List<Article>,
    isLoading: Boolean,
    error: String?,
    onOpenDetail: (Article) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { articles.size })
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "YoBreaking News",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = {}) {
                Text(
                    text = "View all",
                    // color = Color(0xFF007AFF),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 4.dp,
            modifier = Modifier
                .height(180.dp)
                .padding(end = 16.dp),
            key = { page -> articles[page].title ?: articles[page].title ?: page }
        ) { page ->
            BreakingNewsCard(
                sharedTransitionScope,
                animatedContentScope,
                articles[page]
            ) { onOpenDetail(articles[page]) }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if(pagerState.pageCount > 1){
            AppPagerIndicator(
                pagerState= pagerState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BreakingNewsCard(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    article: Article,
    onClick: () -> Unit
) {
    with(sharedTransitionScope) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF4A4A4A))
                .clickable(onClick = onClick),
        ) {
            // Placeholder for image
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF6B8E6B),
                                Color(0xFF2C2C2C)
                            )
                        )
                    )
            ) {
                ArticleImage(
                    imageUrl = article.urlToImage,
                    sourceName = null,
                    shape = ArticleImageShape.Sharp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = "image-${article.id}"),
                            animatedVisibilityScope = animatedContentScope
                        )
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFF007AFF),
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(
                        text = "Technology",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = article.sourceName ?: "",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        // if (news.verified) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = Color(0xFF007AFF),
                            modifier = Modifier.size(16.dp)
                        )
                        // }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "• ${getTimeAgo(article.publishedAt ?: "")}",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = article.title ?: "",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "text-${article.id}"),
                                animatedVisibilityScope = animatedContentScope,
                            )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun BreakingNewsSectionPreview() {
    YoNewsAiTheme {
        SharedTransitionLayout {
            AnimatedContent(targetState = true, label = "preview_news") { targetState ->
                if (targetState){

                }
                with(this@SharedTransitionLayout){
                    BreakingNewsSection(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@AnimatedContent,
                        articles = List(3) { page ->
                            Article(
                                id = page,
                                title = "$page Breaking News: Kotlin Compose Rocks 🚀",
                                description = "Compose simplifies Android UI development with a declarative approach that makes building beautiful UIs easier than ever.",
                                sourceName = "OpenAI News",
                                urlToImage = "https://picsum.photos/seed/picsum/200/300",
                                author = "Yogi Arif Widodo",
                                url = "https://www.washingtonpost.com/politics/2025/10/02/trump-fda-abortion-pill/",
                                publishedAt = "2025-10-02T20:12:15Z",
                                content = "The FDA has little latitude to reject generic versions of approved drugs."
                            )
                        },
                        isLoading = false,
                        error = null,
                        onOpenDetail = {}
                    )
                }

            }
        }
    }
}