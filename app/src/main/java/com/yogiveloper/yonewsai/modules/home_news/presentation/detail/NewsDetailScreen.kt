package com.yogiveloper.yonewsai.modules.home_news.presentation.detail

import android.content.Intent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.yogiveloper.yonewsai.R
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.ArticleDetailBody
import com.yogiveloper.yonewsai.ui.theme.Adrenaline

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun NewsDetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    vm: NewsDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val ctx = LocalContext.current
    val state by vm.uiState.collectAsState()
    val article = state.article ?: return

    Box(modifier = Modifier.fillMaxSize()) {
        ArticleDetailContent(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope,
            article = article,
            onOpenOriginal = {
                article.url?.let { url ->
                    ctx.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                }
            }
        )

        // Top Navigation Bar (Floating)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back Button
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = { /* TODO: Bookmark */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = { /* TODO: More options */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ArticleDetailContent(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    article: Article,
    modifier: Modifier = Modifier,
    onOpenOriginal: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Image with Overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            with(sharedTransitionScope) {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState("image-${article.id}"),
                            animatedVisibilityScope = animatedContentScope
                        ),
                    contentScale = ContentScale.Crop
                )
            }

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 200f
                        )
                    )
            )

            // Category Badge & Title
            with(sharedTransitionScope) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                        .padding(bottom = 8.dp)
                ) {
                    // Category Badge
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFF007AFF),
                        modifier = Modifier.padding(bottom = 12.dp)
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "badge-${article.id}"),
                                animatedVisibilityScope = animatedContentScope
                            )
                    ) {
                        Text(
                            text = stringResource(R.string.technology),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }

                    // Title
                    Text(
                        text = article.title ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 30.sp,
                        modifier = Modifier.sharedElement(
                            sharedTransitionScope.rememberSharedContentState("text-${article.id}"),
                            animatedVisibilityScope = animatedContentScope
                        ),
                    )


                    // Metadata
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.trending),
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.9f),
                        )
                        Text(
                            text = " • ",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.7f),
                        )
                        Text(
                            text = article.publishedAt ?: "- hours ago",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.sharedElement(
                                sharedTransitionScope.rememberSharedContentState("text-time-${article.id}"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                        )
                    }
                }
            }
        }

        // Content Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Source with verified badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    // Source Logo Placeholder (you can add actual logo here)
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = Adrenaline
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "CNN",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = article.sourceName ?: "CNN Indonesia",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    // Verified Badge
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_secure), // Use appropriate verified icon
                        contentDescription = "Verified",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Article Content
                ArticleDetailBody(article, onOpenOriginal)
            }
        }
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