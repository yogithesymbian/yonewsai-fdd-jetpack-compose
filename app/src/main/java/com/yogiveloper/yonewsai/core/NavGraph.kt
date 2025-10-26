package com.yogiveloper.yonewsai.core

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.detail.NewsDetailScreen
import com.yogiveloper.yonewsai.modules.home_news.presentation.home.NewsHomeScreen
import com.yogiveloper.yonewsai.modules.home_news.presentation.list.NewsListScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { backStackEntry ->
                NewsHomeScreen(
                    this@SharedTransitionLayout,
                    this@composable,
                            onOpenDetail = { article ->
                                // navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                                backStackEntry.savedStateHandle["article"] = article
                                navController.navigate("detail/${article.id}")
                            }
                )
            }
            composable(
                "detail/{item}",
                arguments = listOf(navArgument("item") { type = NavType.IntType } )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("item")
                val article =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
                NewsDetailScreen(
                    this@SharedTransitionLayout,
                    this@composable,
                    id = id,
                    article = article,
                    onBack = { navController.navigateUp() }
                )
            }
            composable("list") {
                NewsListScreen(onOpenDetail = { article ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                    navController.navigate("detail")
                })
            }
        }
    }
}
