package com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogiveloper.yonewsai.R
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules.ArticleImage
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules.ArticleImageShape

data class RecommendationItem(
    val id: Int,
    val category: String,
    val title: String,
    val description: String,
    val author: String,
    val date: String
)

@Composable
fun RecommendationSection() {
    val recommendations = listOf(
        RecommendationItem(
            id = 1,
            category = "Sports",
            title = "What Training Do Vollyball Players Need?",
            description = "yogi lorem sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            author = "yogiveloper",
            date = "Feb 27, 2023"
        ),
        RecommendationItem(
            id = 2,
            category = "Education",
            title = "Secondary school places: When do parents find out?",
            description = "arif lorem sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            author = "arif",
            date = "Feb 28, 2023"
        ),
        RecommendationItem(
            id = 3,
            category = "Energy",
            title = "Green Energy is a Future for Our Planet",
            description = "w lorem sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            author = "scodeid",
            date = "Feb 29, 2023"
        )
    )

    Crossfade(
        targetState = true,
        animationSpec = tween(durationMillis = 500), // 0.5 second animation
        label = "StateCrossfade"
    ) { state ->
        when(state){
            else -> {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recommendation",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        TextButton(onClick = {}) {
                            Text(
                                text = "View all",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

//                    LazyColumn(
//                        modifier = Modifier.fillMaxSize(),
//                        contentPadding = PaddingValues(vertical = 8.dp)
//                    ) {
//                        items(recommendations, key = { it.id }) { item ->
                        recommendations.forEach { item ->
                            RecommendationCard(
                                modifier = Modifier
//                                    .animateItem(
//                                        placementSpec = tween(durationMillis = 600)
//                                    )
                                    ,
                                item
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
//                    }
                }
            }
        }
    }
}

@Composable
fun RecommendationCard(
    modifier: Modifier = Modifier,
    item: RecommendationItem
) {
    // State to control whether the description is shown
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.fillMaxWidth()
            // --- SECOND KEY ---
            // The card will animate its size
            // when the description appears or disappears
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE5E5EA))
            ) {
                ArticleImage(
                    imageUrl = "https://picsum.photos/seed/picsum/200/300",
                    sourceName = null,
                    shape = ArticleImageShape.Sharp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

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
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 20.sp
                    )
                    // --- EXPANDABLE DESCRIPTION ---
                    if (item.description.isNotBlank()) {
                        // The description will show or hide with animation
                        AnimatedVisibility(visible = isExpanded) {
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                            )
                        }

                        // "Read more / Hide" text
                        Text(
                            text = if (isExpanded) stringResource(R.string.hide) else stringResource(
                                R.string.read_more
                            ),
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
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "• ${item.date}",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}