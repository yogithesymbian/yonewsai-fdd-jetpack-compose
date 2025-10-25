package com.yogiveloper.yonewsai.core


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.detail.NewsDetailScreen
import com.yogiveloper.yonewsai.modules.home_news.presentation.home.NewsHomeScreen
import com.yogiveloper.yonewsai.modules.home_news.presentation.list.NewsListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            NewsHomeScreen(onOpenDetail = { article ->
                navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                navController.navigate("detail")
            })
        }
        composable("list") {
            NewsListScreen(onOpenDetail = { article ->
                navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                navController.navigate("detail")
            })
        }
        composable("detail") {
            val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
            NewsDetailScreen(article = article, onBack = { navController.popBackStack() })
        }
    }
}
