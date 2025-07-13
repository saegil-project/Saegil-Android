package com.saegil.news.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.model.NewsItem
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
    private val saveTopicUseCase: SavePreferredTopicsUseCase,
    //private val getNewsUseCase: GetTopicNewsUseCase,
    getTopicUseCase: GetPreferredTopicsUseCase
) : ViewModel() {

    val newsUiState: StateFlow<NewsUiState> = getTopicUseCase()
        .map { topics ->
            if (topics.isEmpty()) {
                NewsUiState.NoTopics
            } else {
                //NewsUiState.Success(getNewsUseCase(topics))
                NewsUiState.Success(listOf(NewsItem(
                    title = "서울 올해 첫 폭염주의보… 밤에도 무더위 계속",
                    topic = "날씨",
                    date = "2025.06.30",
                    imageUrl = ""
                ),
                    NewsItem(
                        title = "서울 올해 첫 폭염주의보… 밤에도 무더위 계속",
                        topic = "날씨",
                        date = "2025.06.30",
                        imageUrl = ""
                    ),
                    NewsItem(
                        title = "서울 올해 첫 폭염주의보… 밤에도 무더위 계속",
                        topic = "날씨",
                        date = "2025.06.30",
                        imageUrl = ""
                    ),
                    NewsItem(
                        title = "서울 올해 첫 폭염주의보… 밤에도 무더위 계속",
                        topic = "날씨",
                        date = "2025.06.30",
                        imageUrl = ""
                    ),
                    NewsItem(
                        title = "서울 올해 첫 폭염주의보… 밤에도 무더위 계속",
                        topic = "날씨",
                        date = "2025.06.30",
                        imageUrl = ""
                    )))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NewsUiState.Loading
        )

    fun savePreferredTopics(topics: List<String>) {
        viewModelScope.launch {
            saveTopicUseCase(topics)
        }
    }

    fun clearPreferredTopics() = viewModelScope.launch { saveTopicUseCase(emptyList()) }

}