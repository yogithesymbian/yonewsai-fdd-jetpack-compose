package com.yogiveloper.yonewsai.ui.molecules

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun LoadingState() {
    // Replace CircularProgressIndicator with placeholder list
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        // userScrollEnabled = false // Disable scrolling while loading
    ) {
        items(10) { // Show 10 shimmer items
            ShimmerArticleCard()
        }
    }
}