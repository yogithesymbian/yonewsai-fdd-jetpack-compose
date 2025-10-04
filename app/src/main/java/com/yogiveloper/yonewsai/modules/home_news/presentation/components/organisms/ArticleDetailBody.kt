package com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.ui.atoms.button.AppButton
import com.yogiveloper.yonewsai.ui.atoms.button.AppButtonShape
import com.yogiveloper.yonewsai.ui.atoms.button.AppButtonVariant
import com.yogiveloper.yonewsai.ui.theme.YoNewsAiTheme

@Composable
fun ArticleDetailBody(
    article: Article,
    onOpenOriginal: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ){
        if (!article.description.isNullOrEmpty()) {
            Text(
                text = article.description,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp),
                lineHeight = MaterialTheme.typography.titleMedium.lineHeight
            )
        }

        Text(
            text = article.content ?: article.description ?: "No content available.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Want to read more?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                AppButton(
                    text = "Read Full Article",
                    onClick = onOpenOriginal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = AppButtonShape.Rounded,
                    variant = AppButtonVariant.Primary,
                    icon = Icons.Default.OpenInBrowser
                )
            }
        }
        Text(
            text = "This is a preview. Full article available at the source.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsDetailBodyScreenPreview() {
    YoNewsAiTheme {
        ArticleDetailBody(
            article = Article(
                title = "Breaking News: Kotlin Compose Revolutionizes Android Development",
                description = "Jetpack Compose is transforming how developers build Android UIs with its modern, declarative approach that simplifies the development process.",
                sourceName = "Tech News Daily",
                urlToImage = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/2GL6CGJ2H276MXKU5XT6XH6IYE.jpg&w=1440",
                author = "Yogi Arif Widodo",
                url = "https://example.com/article",
                publishedAt = "2025-10-02T20:12:15Z",
                content = "Jetpack Compose has emerged as a game-changer in Android development. The modern toolkit enables developers to build beautiful, responsive UIs with significantly less code than traditional View-based approaches. With its declarative syntax and powerful state management, Compose is quickly becoming the preferred choice for Android developers worldwide."
            ),
            onOpenOriginal = {}
        )
    }
}