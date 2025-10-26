package com.yogiveloper.yonewsai.core

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                                // backStackEntry.savedStateHandle["article"] = article
                                navController.navigate("detail/${article.id}")
                            }
                )
            }
            composable(
                "detail/{articleID}",
                arguments = listOf(navArgument("articleID") { type = NavType.IntType } )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("articleID")
                // val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
                if (id == null) {
                    navController.popBackStack()
                    return@composable
                }
                NewsDetailScreen(
                    this@SharedTransitionLayout,
                    this@composable,
                    onBack = {
                        if (backStackEntry.lifecycle.currentState == androidx.lifecycle.Lifecycle.State.RESUMED) {
                            navController.popBackStack()
                        }
                    }
                )
            }
            composable("list") {
                NewsListScreen(onOpenDetail = { article ->
                    // navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                    navController.navigate("detail")
                })
            }
        }
    }
}
