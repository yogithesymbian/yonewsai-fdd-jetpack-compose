package com.yogiveloper.yonewsai.modules.home_news.domain.usecase

import com.yogiveloper.yonewsai.core.util.Resource
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.domain.repository.NewsRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetTopHeadlinesUseCaseTest {
    private val repo: NewsRepository = mock()
    private lateinit var useCase: GetTopHeadlinesUseCase

    @Before
    fun setup() {
        useCase = GetTopHeadlinesUseCase(repo)
    }

    @Test
    fun whenRepositoryReturnsArticles_UseCaseReturnsSuccess() {
        runBlocking {
            val fake = listOf(
                Article(
                    1,
                    "Author",
                    "Title",
                    "Desc",
                    "url",
                    "img",
                    "2025-10-01",
                    "content",
                    ""
                )
            )
            whenever(repo.getTopHeadlines()).thenReturn(fake)

            val res = useCase()
            assertTrue(res is Resource.Success)
            val data = (res as Resource.Success).data
            assertEquals(1, data.size)
            assertEquals("Title", data[0].title)
        }
    }
}