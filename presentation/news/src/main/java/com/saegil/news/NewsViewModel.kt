package com.saegil.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.GetPreferredTopicsUseCase
import com.saegil.domain.usecase.SavePreferredTopicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val saveUseCase: SavePreferredTopicsUseCase,
    getUseCase: GetPreferredTopicsUseCase
) : ViewModel() {

    val newsUiState: StateFlow<NewsUiState> = getUseCase()
        .map { topics ->
            if (topics.isEmpty()) {
                NewsUiState.NoTopics
            } else {
                NewsUiState.Success(topics)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NewsUiState.Loading
        )

    fun savePreferredTopics(topics: List<String>) {
        viewModelScope.launch {
            saveUseCase(topics)
        }
    }

}