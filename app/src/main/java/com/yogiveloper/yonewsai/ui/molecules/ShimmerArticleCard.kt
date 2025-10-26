package com.yogiveloper.yonewsai.ui.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

/**
 * A shimmer placeholder for an article card.
 *
 * This composable is flexible and can be used in both vertical and horizontal lists.
 * - For vertical lists, it will fill the available width.
 * - For horizontal lists, it uses a default width of 280.dp.
 *
 * @param modifier The modifier to be applied to the Card. Use this to control size and spacing from the outside.
 */
@Composable
fun ShimmerArticleCard(
    modifier: Modifier = Modifier
) {
    val shimmerColor = Color.LightGray.copy(alpha = 0.6f)

    Card(
        // Apply the incoming modifier here.
        // This allows the caller to define size, padding, etc.
        modifier = modifier.shimmer(),
    ) {
        Column {
            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(shimmerColor)
            )
            // Text placeholder
            Column(modifier = Modifier.padding(16.dp)) {
                // Title placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(shimmerColor)
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Subtitle placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(16.dp)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
                        .background(shimmerColor)
                )
            }
        }
    }
}


// --- PREVIEWS TO VERIFY BOTH LAYOUTS ---

@Preview(name = "Vertical Shimmer Card", showBackground = true)
@Composable
private fun ShimmerArticleCardVerticalPreview() {
    ShimmerArticleCard(
        // For vertical, we add padding and let it fill the width by default (inside a Column)
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Preview(name = "Horizontal Shimmer Card", showBackground = true)
@Composable
private fun ShimmerArticleCardHorizontalPreview() {
    ShimmerArticleCard(
        // For horizontal, we explicitly set a width.
        modifier = Modifier.width(280.dp)
    )
}
