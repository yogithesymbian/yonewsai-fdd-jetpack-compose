package com.yogiveloper.yonewsai.ui.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
    indicatorWidth: Int = 8,
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(indicatorWidth.dp)
    ){
        repeat(pagerState.pageCount) { it ->
            val color = if(pagerState.currentPage == it) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .size(indicatorWidth.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}