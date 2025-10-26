package com.yogiveloper.yonewsai.modules.home_news.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogiveloper.yonewsai.core.util.Resource
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.domain.usecase.GetArticleByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewsDetailUiState(
    val article: Article? = null
)

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsDetailUiState())
    val uiState: StateFlow<NewsDetailUiState> = _uiState

    init {
        val articleID = savedStateHandle.get<Int>("articleID")
        if(articleID !== null){
            fetchArticleByID(articleID)
        }
    }

    private fun fetchArticleByID(id: Int){
        viewModelScope.launch {
            when(val r = getArticleByIdUseCase(id)){
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(article = r.data)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(article = null)
                }
                Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(article = null)
                }
            }
        }
    }
}