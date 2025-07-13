package com.saegil.news

sealed interface NewsUiState {

    data object Loading : NewsUiState

    data object NoTopics : NewsUiState

    data class Success (
        val preferredTopics : List<String>,
    ) : NewsUiState

}