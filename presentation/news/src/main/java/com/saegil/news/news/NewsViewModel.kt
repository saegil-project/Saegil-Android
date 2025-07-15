package com.saegil.news.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.model.Interest
import com.saegil.domain.usecase.GetCategoryUseCase
import com.saegil.domain.usecase.GetPreferredTopicsUseCase
import com.saegil.domain.usecase.GetTopicNewsUseCase
import com.saegil.domain.usecase.SavePreferredTopicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val saveTopicUseCase: SavePreferredTopicsUseCase,
    private val getNewsUseCase: GetTopicNewsUseCase,
    getCategoryUseCase: GetCategoryUseCase,
    getTopicUseCase: GetPreferredTopicsUseCase
) : ViewModel() {

    val newsUiState: StateFlow<NewsUiState> = combine(
        getTopicUseCase(),
        getCategoryUseCase()
    ) { topics, categories ->
        Log.d("경로 NewsViewModel", "topics: $topics, categories: $categories")
        if (topics.isEmpty()) {
            NewsUiState.NoTopics(categories)
        } else {
            NewsUiState.Success(getNewsUseCase())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = NewsUiState.Loading
    )

    fun savePreferredTopics(topics: List<Interest>) {
        viewModelScope.launch {
            saveTopicUseCase(topics)
        }
    }

    fun clearPreferredTopics() = viewModelScope.launch { saveTopicUseCase(emptyList()) }

}