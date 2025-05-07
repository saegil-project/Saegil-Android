package com.saegil.log

import com.saegil.domain.model.SimulationLogDetail

sealed interface LogUiState {

    data object Loading : LogUiState

    data class Success (
        val detail : SimulationLogDetail
    ) : LogUiState

}