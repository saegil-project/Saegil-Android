package com.saegil.log

import kotlinx.coroutines.flow.Flow

sealed interface LogUiState {

    data object Loading : LogUiState

    data class Success (
        val messages : Flow<PagingData<Message>>
    ) : LogUiState

}