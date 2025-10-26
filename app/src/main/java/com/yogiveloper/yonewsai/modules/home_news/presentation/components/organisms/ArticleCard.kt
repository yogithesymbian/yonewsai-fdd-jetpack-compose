package com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.yogiveloper.yonewsai.core.util.time.formatPublishedDate
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.ui.theme.YoNewsAiTheme


@Composable
fun ArticleCard(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State to control whether the description is shown
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier // Use the modifier that was passed in
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            // --- SECOND KEY ---
            // The card will animate its size
            // when the description appears or disappears
            .animateContentSize(
                animationSpec = spring()
            )
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp).animateContentSize()) {
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                // --- EXPANDABLE DESCRIPTION ---
                if (!article.description.isNullOrBlank()) {
                    // The description will show or hide with animation
                    AnimatedVisibility(visible = isExpanded) {
                        Text(
                            text = article.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                        )
                    }

                    // "Read more / Hide" text
                    Text(
                        text = if (isExpanded) "Sembunyikan" else "Baca selengkapnya...",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null // No ripple effect needed
                            ) {
                                isExpanded = !isExpanded
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!article.author.isNullOrEmpty()) {
                        Text(
                            text = article.author,
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (!article.publishedAt.isNullOrEmpty()) {
                        Text(
                            text = formatPublishedDate(article.publishedAt),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    YoNewsAiTheme {
        ArticleCard(
            article = Article(
                id = 1,
                title = "Breaking News: Kotlin Compose Rocks 🚀",
                description = "Compose simplifies Android UI development with a declarative approach that makes building beautiful UIs easier than ever.",
                sourceName = "OpenAI News",
                urlToImage = "https://picsum.photos/200/300",
                author = "Yogi Arif Widodo",
                url = "https://www.washingtonpost.com/politics/2025/10/02/trump-fda-abortion-pill/",
                publishedAt = "2025-10-02T20:12:15Z",
                content = "The FDA has little latitude to reject generic versions of approved drugs."
            ),
            onClick = {}
        )
    }
}