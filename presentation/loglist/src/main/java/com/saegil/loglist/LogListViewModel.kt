package com.saegil.loglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.saegil.domain.model.Scenario
import com.saegil.domain.usecase.GetScenarioLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LogListViewModel @Inject constructor(
    getScenarioLogUseCase: GetScenarioLogUseCase
) : ViewModel() {

    val logUiState: StateFlow<LogListUiState> = flow<Flow<PagingData<Scenario>>> {
        getScenarioLogUseCase()
    }.map<Flow<PagingData<Scenario>>, LogListUiState>(LogListUiState::Success)
        .onStart { emit(LogListUiState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LogListUiState.Loading,
        )

}