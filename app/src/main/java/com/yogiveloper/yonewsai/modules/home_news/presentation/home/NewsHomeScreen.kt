package com.yogiveloper.yonewsai.modules.home_news.presentation.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.BreakingNewsSection
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms.RecommendationSection


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NewsHomeScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    vm: NewsHomeViewModel = hiltViewModel(),
    onOpenDetail: (Article) -> Unit
) {
    val state by vm.uiState.collectAsState()
    NewsHomeContent(
        sharedTransitionScope,
        animatedContentScope,
        articles = state.articles,
        isLoading = state.isLoading,
        error = state.error,
        onOpenDetail = onOpenDetail
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NewsHomeContent(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
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
                // .background(Color(0xFFF5F5F5))
                .padding(padding)
        ) {
            item {
                BreakingNewsSection(
                    sharedTransitionScope,
                    animatedContentScope,
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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                // tint = Color.Black
            )
        }

        Row {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    // tint = Color.Black
                )
            }
            Box {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        // tint = Color.Black
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

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
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