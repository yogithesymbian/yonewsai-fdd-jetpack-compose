package com.yogiveloper.yonewsai.modules.home_news.presentation.list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import org.junit.Rule
import org.junit.Test

class NewsListScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun articleTitleIsDisplayed() {
        val items = listOf(
            Article(
                "Source",
                "Author",
                "Test Title",
                "Desc",
                "url",
                "img",
                "date",
                "content"
            )
        )

        rule.setContent {
            NewsListContent(
                articles = items,
                isLoading = false,
                error = null,
                onRefresh = {},
                onOpenDetail = {}
            )
        }

        rule.onNodeWithText("Test Title").assertExists()
    }
}
