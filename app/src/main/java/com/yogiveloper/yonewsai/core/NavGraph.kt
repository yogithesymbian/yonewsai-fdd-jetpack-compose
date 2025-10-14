package com.yogiveloper.yonewsai.core


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.presentation.detail.NewsDetailScreen
import com.yogiveloper.yonewsai.modules.home_news.presentation.list.NewsListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            NewsListScreen(onOpenDetail = { article ->
                /*
                * Explain of Parcelable check on #explain_01
                * : Parcelable
                * None of the following functions can be called with the arguments supplied:
                * public open fun <V : Any> set(key: String, value: V?): Unit defined in androidx.lifecycle.SavedStateHandle
                *
                * Tipe data Article tidak cocok.
                * Saya (SavedStateHandle) hanya menerima tipe data yang bisa
                * saya simpan (seperti String, Int, atau Parcelable),
                * dan Article Anda bukan salah satunya.
                *
                * Cannot use 'Article' as a reified type parameter. Use a class instead.
                * Saya tidak bisa mengambil data dan mengubahnya menjadi
                * tipe Article, karena Article bukan tipe yang bisa direkonstruksi
                * (bukan Parcelable)
                * */
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
