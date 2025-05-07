package com.saegil.loglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.model.SimulationLog
import com.saegil.domain.usecase.GetSimulationLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LogListViewModel @Inject constructor(
    getSimulationLogUseCase: GetSimulationLogUseCase
) : ViewModel() {

    val logUiState: StateFlow<LogListUiState> =
        getSimulationLogUseCase()
            .map<List<SimulationLog>?, LogListUiState>(LogListUiState::Success)
            .onStart { emit(LogListUiState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LogListUiState.Loading,
            )

}