package com.saegil.news.newsquiz

import com.saegil.domain.model.Quiz

interface NewsQuizUiState {

    data object Loading : NewsQuizUiState

    data class Success(
        val quiz: Quiz
    ) : NewsQuizUiState

}