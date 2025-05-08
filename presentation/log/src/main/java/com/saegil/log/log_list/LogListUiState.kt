package com.saegil.log.log_list

import com.saegil.domain.model.SimulationLog

sealed interface LogListUiState {
    data object Loading : LogListUiState

    data class Success (
        val logList : List<SimulationLog>?
    ) : LogListUiState
}