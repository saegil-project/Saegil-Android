package com.saegil.announcement.announcement

import androidx.paging.PagingData
import com.saegil.domain.model.Notice
import kotlinx.coroutines.flow.Flow

sealed interface NoticeUiState {

    data object Loading : NoticeUiState

    data class Success (
        val feeds : Flow<PagingData<Notice>>
    ) : NoticeUiState

}