package com.yogiveloper.yonewsai.ui.organisms


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.valentinilk.shimmer.*

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
