package com.yogiveloper.yonewsai.modules.home_news.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(article: Article?, onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(article?.title ?: "Detail" , maxLines = 2 ) },
            navigationIcon = {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
            }
        )
    }) { padding ->
        val ctx = LocalContext.current
        Column(modifier = Modifier
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
        ) {
            article?.urlToImage?.let {
                AsyncImage(model = it, contentDescription = null, modifier = Modifier.fillMaxWidth().height(220.dp))
                Spacer(Modifier.height(8.dp))
            }
            Text(text = article?.title ?: "", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text(text = article?.content ?: article?.description ?: "No content", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(12.dp))
            Button(onClick = {
                article?.url?.let { url ->
                    ctx.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            }) {
                Text("Open original")
            }
        }
    }
}
