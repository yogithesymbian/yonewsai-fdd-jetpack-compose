package com.yogiveloper.yonewsai.modules.home_news.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.yogiveloper.yonewsai.core.util.time.formatPublishedDate
import com.yogiveloper.yonewsai.core.util.time.getTimeAgo
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules.ArticleImage
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules.ArticleImageShape


//@Preview(showBackground = true)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewsHomeScreen(
    vm: NewsHomeViewModel = hiltViewModel(),
    onOpenDetail: (Article) -> Unit
) {
    val state by vm.uiState.collectAsState()
    NewsHomeContent(
        articles = state.articles,
        isLoading = state.isLoading,
        error = state.error,
        onOpenDetail = onOpenDetail
    )
}

@Composable
fun NewsHomeContent(
    articles: List<Article>,
    isLoading: Boolean,
    error: String?,
    onOpenDetail: (Article) -> Unit
) {
    Scaffold(
        topBar = { HomeAppBar() },
        bottomBar = { BottomNavigationBar() }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
        ) {
            item {
                BreakingNewsSection(
                    articles = articles,
                    isLoading = isLoading,
                    error = error,
                    onOpenDetail = onOpenDetail
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                RecommendationSection()
            }
        }
    }
}

@Composable
fun HomeAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.Black
            )
        }

        Row {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Black
                )
            }
            Box {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.Black
                    )
                }
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Red, CircleShape)
                        .align(Alignment.TopEnd)
                        .offset(x = (-4).dp, y = 8.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BreakingNewsSectionPreview() {
//    BreakingNewsSection()
//}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun BreakingNewsSection(
    articles: List<Article>,
    isLoading: Boolean,
    error: String?,
    onOpenDetail: (Article) -> Unit
) {
    val pagerState = rememberPagerState()
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
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            TextButton(onClick = {}) {
                Text(
                    text = "View all",
                    color = Color(0xFF007AFF),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            count = articles.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            itemSpacing = 4.dp,
            modifier = Modifier.height(220.dp)
                .padding(end = 16.dp)
        ) { page ->
            BreakingNewsCard(articles[page])
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            activeColor = Color(0xFF007AFF),
            inactiveColor = Color(0xFFD1D1D6)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BreakingNewsSectionPreview() {
    BreakingNewsSection(
        articles = List(3){ page ->
            Article(
                title = "$page Breaking News: Kotlin Compose Rocks 🚀",
                description = "Compose simplifies Android UI development with a declarative approach that makes building beautiful UIs easier than ever.",
                sourceName = "OpenAI News",
                urlToImage = "https://abcnews.go.com/US/large-fire-erupts-chevron-refinery-southern-california/story?id\\\\\\\\u003d126171692",
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



@Composable
fun BreakingNewsCard(article: Article) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF4A4A4A))
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
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun RecommendationSection() {
    val recommendations = listOf(
        RecommendationItem(
            category = "Sports",
            title = "What Training Do Vollyball Players Need?",
            author = "McKindney",
            date = "Feb 27, 2023"
        ),
        RecommendationItem(
            category = "Education",
            title = "Secondary school places: When do parents find out?",
            author = "Rosemary",
            date = "Feb 28, 2023"
        )
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recommendation",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            TextButton(onClick = {}) {
                Text(
                    text = "View all",
                    color = Color(0xFF007AFF),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        recommendations.forEach { item ->
            RecommendationCard(item)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RecommendationCard(item: RecommendationItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFE5E5EA))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = item.category,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    lineHeight = 20.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE5E5EA))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.author,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "• ${item.date}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(70.dp)
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color(0xFF007AFF),
                indicatorColor = Color(0xFF007AFF),
                unselectedIconColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = "Explore"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray
            )
        )
    }
}

data class NewsItem(
    val category: String,
    val source: String,
    val time: String,
    val title: String,
    val verified: Boolean
)

data class RecommendationItem(
    val category: String,
    val title: String,
    val author: String,
    val date: String
)