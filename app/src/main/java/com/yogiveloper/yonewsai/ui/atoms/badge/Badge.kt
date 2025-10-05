package com.yogiveloper.yonewsai.ui.atoms.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yogiveloper.yonewsai.ui.theme.YoNewsAiTheme

@Composable
fun AppBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black.copy(alpha = 0.6f),
    textColor: Color = Color.White
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun AppBadgePreview() {
    YoNewsAiTheme {
        AppBadge(text = "Tech News Daily")
    }
}