package com.saegil.announcement.announcement

import com.saegil.data.NewsResource

sealed interface AnnouncementUiState {

    data object Loading : AnnouncementUiState

    data class Success(
        val feed: List<NewsResource>
    ) : AnnouncementUiState
}