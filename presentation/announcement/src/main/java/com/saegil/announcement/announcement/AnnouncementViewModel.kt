package com.saegil.announcement.announcement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.data.model.NewsResource
import com.saegil.data.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    feedRepository: FeedRepository
) : ViewModel() {

    val feedUiState: StateFlow<AnnouncementUiState> =
        feedRepository.getFeeds()
            .map<List<NewsResource>, AnnouncementUiState>(AnnouncementUiState::Success)
            .onStart { emit(AnnouncementUiState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AnnouncementUiState.Loading,
            )

}