package com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules.ArticleImage
import com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules.ArticleImageShape

data class RecommendationItem(
    val category: String,
    val title: String,
    val author: String,
    val date: String
)

@Composable
fun RecommendationSection() {
    val recommendations = listOf(
        RecommendationItem(
            category = "Sports",
            title = "What Training Do Vollyball Players Need?",
            author = "McKindney",
            date = "Feb 27, 2023"
        ),
        RecommendationItem(
            category = "Education",
            title = "Secondary school places: When do parents find out?",
            author = "Rosemary",
            date = "Feb 28, 2023"
        )
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recommendation",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            TextButton(onClick = {}) {
                Text(
                    text = "View all",
                    color = Color(0xFF007AFF),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        recommendations.forEach { item ->
            RecommendationCard(item)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RecommendationCard(item: RecommendationItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
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
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    lineHeight = 20.sp
                )
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
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "• ${item.date}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}