package com.yogiveloper.yonewsai.modules.home_news.presentation.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.yogiveloper.yonewsai.ui.atoms.badge.AppBadge
import com.yogiveloper.yonewsai.ui.molecules.ImageWithOverlay
import com.yogiveloper.yonewsai.ui.organisms.AppShimmer
import com.yogiveloper.yonewsai.ui.theme.YoNewsAiTheme

/**
 * Defines the visual shape variants for the ArticleImage.
 */
enum class ArticleImageShape {
    /** A square shape with sharp (0.dp) corners. */
    Sharp,
    /** Only the top corners are rounded, suitable for cards. */
    CardTop,
    /** All corners are rounded. */
    Rounded,
}


/**
 * A reusable component to display an article's image with an optional overlay and source badge.
 *
 * @param imageUrl The URL of the image to display.
 * @param sourceName The name of the news source for the badge.
 * @param modifier The modifier to be applied to the component, allowing the caller to control size and placement.
 * @param shape The visual shape variant to apply to the image corners.
 */
@Composable
fun ArticleImage(
    imageUrl: String?,
    sourceName: String?,
    modifier: Modifier = Modifier,
    shape: ArticleImageShape = ArticleImageShape.CardTop
) {
    val imageShape: Shape = when (shape) {
        ArticleImageShape.Sharp -> RoundedCornerShape(0.dp)
        ArticleImageShape.CardTop -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ArticleImageShape.Rounded -> RoundedCornerShape(16.dp)
    }
    Box(
        modifier = modifier
            .clip(imageShape)
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = {
                AppShimmer(modifier = Modifier.fillMaxSize())
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Image not available", color = Color.Gray)
                }
            }
        )

        ImageWithOverlay()

        if (!sourceName.isNullOrEmpty()) {
            AppBadge(
                sourceName,
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleImagePreview(){
    YoNewsAiTheme {
        ArticleImage(
            imageUrl = "",
            sourceName = "BADGE",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}