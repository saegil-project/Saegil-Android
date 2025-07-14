package com.saegil.news.newsquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.model.Quiz
import com.saegil.domain.usecase.GetQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsQuizViewModel @Inject constructor(
    getQuizUseCase: GetQuizUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val quizId: Long = checkNotNull(savedStateHandle["quizId"])

    val newsQuizUiState: StateFlow<NewsQuizUiState> =
        getQuizUseCase(quizId)
            .map<Quiz, NewsQuizUiState>(NewsQuizUiState::Success)
            .onStart { emit(NewsQuizUiState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = NewsQuizUiState.Loading,
            )

}