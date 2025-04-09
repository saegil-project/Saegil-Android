package com.saegil.announcement.announcement

import com.saegil.domain.model.Notice


sealed interface AnnouncementUiState {

    data object Loading : AnnouncementUiState

    data class Success(
        val feed: List<Notice>
    ) : AnnouncementUiState

}