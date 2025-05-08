package com.saegil.learning.learning_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.model.Scenario
import com.saegil.domain.usecase.GetScenarioListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LearningListViewModel @Inject constructor(
    private val getScenarioListUseCase: GetScenarioListUseCase
    //private val을 하지 않으면 RuntimeException이 터져 뷰모델을 형성하지 못함
) : ViewModel() {

    val learningListUiState: StateFlow<LearningListUiState> =
        getScenarioListUseCase()
            .map<List<Scenario>, LearningListUiState>(LearningListUiState::Success)
            .onStart { emit(LearningListUiState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LearningListUiState.Loading,
            )
}
