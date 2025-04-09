package com.saegil.announcement.announcement

sealed interface NoticeUiState {

    data object Loading : NoticeUiState

    data object Success : NoticeUiState

}