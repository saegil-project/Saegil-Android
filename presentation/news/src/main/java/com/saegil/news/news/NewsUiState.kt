package com.saegil.news.news

import com.saegil.domain.model.Interest
import com.saegil.domain.model.NewsItem

sealed interface NewsUiState {

    data object Loading : NewsUiState

    data class NoTopics (
        val categories : List<Interest>,
    ) : NewsUiState

    data class Success (
        val newsItems : List<NewsItem>,
    ) : NewsUiState

}