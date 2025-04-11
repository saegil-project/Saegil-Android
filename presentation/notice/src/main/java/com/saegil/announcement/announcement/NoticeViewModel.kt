package com.saegil.announcement.announcement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.saegil.announcement.announcement.NoticeUiState.*
import com.saegil.domain.model.Notice
import com.saegil.domain.usecase.GetFeedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class NoticeViewModel @Inject constructor(
    getFeedUseCase: GetFeedUseCase
) : ViewModel() {

    val feedUiState: StateFlow<NoticeUiState> =
        flowOf(getFeedUseCase())
            .map<Flow<PagingData<Notice>>, NoticeUiState>(NoticeUiState::Success)
            .onStart { emit(Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Loading,
            )

}