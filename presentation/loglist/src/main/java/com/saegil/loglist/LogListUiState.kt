package com.saegil.loglist

import androidx.paging.PagingData
import com.saegil.domain.model.Scenario
import kotlinx.coroutines.flow.Flow

sealed interface LogListUiState {
    data object Loading : LogListUiState

    data class Success (
        val feeds : Flow<PagingData<Scenario>>
    ) : LogListUiState
}