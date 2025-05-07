package com.saegil.log

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.model.SimulationLogDetail
import com.saegil.domain.usecase.GetMessageLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LogViewModel @Inject constructor(
    getMessageLogUseCase: GetMessageLogUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val simulationId: Long = checkNotNull(savedStateHandle["simulationId"])

    val logUiState: StateFlow<LogUiState> =
        getMessageLogUseCase(simulationId)
        .map<SimulationLogDetail,LogUiState>(LogUiState::Success)
        .onStart { emit(LogUiState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LogUiState.Loading,
        )

}