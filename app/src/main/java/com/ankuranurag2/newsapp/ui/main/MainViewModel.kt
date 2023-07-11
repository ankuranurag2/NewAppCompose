package com.ankuranurag2.newsapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankuranurag2.newsapp.domain.models.Article
import com.ankuranurag2.newsapp.domain.usecases.IFetchTopNewsUseCase
import com.ankuranurag2.newsapp.utils.RestClientResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchNewsUseCase: IFetchTopNewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch {
            fetchNewsUseCase.fetchTopNews().collect { response ->
                Log.d("TAGGGG",response.status.name)
                Log.d("TAGGGG",response.message?:"TEST")
                Log.d("TAGGGG",response.data?.message?:"TEST")
                Log.d("TAGGGG",response.data?.getStatus()?.name?:"TEST")
                _uiState.update {
                    it.copy(
                        isLoading = response.status == RestClientResult.Status.LOADING,
                        newsList = response.data?.articles
                    )
                }
            }
        }
    }
}


data class MainUiState(
    val isLoading: Boolean = true,
    val newsList: List<Article>? = null,
)