package com.saegil.announcement.announcement

sealed interface AnnouncementUiState {

    data object Loading : AnnouncementUiState

    data object Success : AnnouncementUiState

}