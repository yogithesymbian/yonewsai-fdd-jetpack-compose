package com.yogiveloper.yonewsai.modules.home_news.presentation.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogiveloper.yonewsai.core.util.Resource
import com.yogiveloper.yonewsai.modules.home_news.domain.model.Article
import com.yogiveloper.yonewsai.modules.home_news.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* merepresentasikan semua elemen visual di layar */
data class NewsListUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null
)

/*
* Kita sudah merakit "mobil" (NetworkModule) dan
* menemukan "sopir"-nya (RepositoryModule).
* Sekarang kita akan melihat bagaimana "sopir" itu bekerja dan
* bagaimana ia menerima perintah dari "manajer" (ViewModel)
* untuk akhirnya ditampilkan di UI.
* */
@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getTopHeadlines: GetTopHeadlinesUseCase // Disediakan Hilt
) : ViewModel() {

    /*
    * 1. State internal yang bisa diubah
    * pola standar di Android. _uiState (dengan garis bawah)
    * bersifat private dan mutable (bisa diubah),
    * hanya ViewModel yang boleh mengubahnya
    * */
    private val _uiState = MutableStateFlow(NewsListUiState())

    /*
    * 2. State publik yang hanya bisa dibaca oleh UI
    * bersifat public dan immutable (hanya bisa dibaca),
    * aman untuk diekspos ke UI.
    *
    * StateFlow bekerja dengan collectAsState(),
    * yang langsung mengubah UI saat state berubah.
    * Tidak perlu lifecycle owner seperti LiveData,
    * karena Compose sudah mengelola lifecycle-nya sendiri.
    * Lebih fleksibel untuk unit testing dan modular architecture,
    * karena tidak bergantung pada Android framework*/
    val uiState: StateFlow<NewsListUiState> = _uiState

    init {
        fetch() // Panggil saat ViewModel pertama kali dibuat
    }

    fun fetch(country: String = "us") {
        viewModelScope.launch {
            /*
            * 3. Update state ke Loading
            * Sebelum memanggil jaringan, ViewModel langsung memperbarui UI
            * untuk menampilkan status loading.
            * Ini memberikan feedback instan kepada pengguna.*/
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            /*
            * 4. Panggil Use Case
            * Memanggil GetTopHeadlinesUseCase seolah-olah itu adalah fungsi biasa.*/
            when (val r = getTopHeadlines(country)) {
                is Resource.Success -> {
                    // 5. Update state ke Success
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        articles = r.data,
                        error = null
                    )
                }
                is Resource.Error -> {
                    // 6. Update state ke Error
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = r.message
                    )
                }
                // (Kasus Loading dari Resource tidak diperlukan di sini
                // karena kita sudah set isLoading = true secara manual)
                Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    fun fetchNextPage(){

    }
}

/*
* Langkah 5 dan 6
* ViewModel menerima hasil berupa Resource dari UseCase dan
* menerjemahkannya menjadi NewsListUiState.
* Ia memetakan Resource.Success menjadi daftar articles dan
* Resource.Error menjadi error message.
* ViewModel tidak tahu menahu tentang Exception, Retrofit, atau API.
* Ia hanya tahu tentang Use Case dan State.*/


/*
* StateFlow X LiveData
* Use: StateFlow karena lebih cocok untuk Jetpack Compose.
* Compose bersifat reactive, dan StateFlow mendukung alur data yang terus mengalir tanpa bergantung pada lifecycle seperti LiveData.
* Ini membuat UI lebih responsif dan arsitektur lebih bersih, terutama saat testing dan modularisasi.
* Selain itu, StateFlow terintegrasi langsung dengan coroutine, sehingga lebih efisien dan konsisten dalam pengelolaan state.
* */