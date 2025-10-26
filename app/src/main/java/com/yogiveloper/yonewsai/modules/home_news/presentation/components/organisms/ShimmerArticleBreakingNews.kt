package com.yogiveloper.yonewsai.modules.home_news.presentation.components.organisms

import androidx.compose.foundation.layout.Arrangement
import com.yogiveloper.yonewsai.ui.molecules.ShimmerArticleCard
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ShimmerArticleBreakingNews() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(3) {
            ShimmerArticleCard(
                modifier = Modifier.width(280.dp)
            )
        }
    }
}