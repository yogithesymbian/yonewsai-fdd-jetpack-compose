package com.yogiveloper.yonewsai.ui.organisms


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.*
import com.yogiveloper.yonewsai.ui.theme.YoNewsAiTheme

@Composable
fun AppShimmer(
    modifier: Modifier = Modifier,
    baseColor: Color = Color.LightGray
) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    Box(
        modifier = modifier
            .shimmer(shimmer)
            .background(baseColor)
    )
}

@Preview(showBackground = true)
@Composable
fun AppShimmerPreview() {
    YoNewsAiTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {
            AppShimmer(modifier = Modifier.fillMaxSize())
        }
    }
}