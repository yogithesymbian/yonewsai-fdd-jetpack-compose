package com.yogiveloper.yonewsai


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.yogiveloper.yonewsai.core.NavGraph
import com.yogiveloper.yonewsai.ui.theme.YoNewsAiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YoNewsAiTheme { // NewsAppTheme // MaterialTheme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavGraph(navController)
                }
            }
        }
    }
}
