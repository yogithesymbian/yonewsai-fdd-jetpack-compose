package com.yogiveloper.yonewsai.modules.home_news.presentation.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogiveloper.yonewsai.core.util.Resource
import com.yogiveloper.yonewsai.core.util.logChanges
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class NewsHomeUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class NewsHomeViewModel @Inject constructor(
    private val getTopHeadlines: GetTopHeadlinesUseCase,
    // private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewsHomeUiState())
    val uiState: StateFlow<NewsHomeUiState> = _uiState

    init {
        uiState.logChanges("NewsHomeViewModel", viewModelScope)
        /**
         * if API doesn't have cache mechanism should use these approach:
         * (else this expensive solution)
         * for HomeScreen should save on repo/local cache (Room/DataStore)
         * */

        /**
         * if API doesn't have or have cache mechanism not problem use these approach:
         * for DetailScreen should save ID on state
         * then getArticleById from repo/local cache (Room/DataStore)
         *  */

        // val cached = savedStateHandle.get<List<Article>>("articles")
        // if (cached != null) {
        //    _uiState.value = _uiState.value.copy(articles = cached)
        // } else {
            fetchBreakingNews()
        // }

    }

    // prevent race-condition ex: on swipe refresh or fast rotate
    private var fetchJob: Job? = null
    fun fetchBreakingNews(country: String = "us", category: String = "technology") {
        fetchJob?.cancel()  // cancel old job if any
        fetchJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when(val r = getTopHeadlines(country, category )){
                is Resource.Success -> {
                    // savedStateHandle["articles"] = r.data
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        articles = r.data,
                        error = null
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = r.message,
                    )
                }
                Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }
}