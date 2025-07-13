package com.saegil.news.news

import com.saegil.domain.model.NewsItem

sealed interface NewsUiState {

    data object Loading : NewsUiState

    data object NoTopics : NewsUiState

    data class Success (
        val newsItems : List<NewsItem>,
    ) : NewsUiState

}