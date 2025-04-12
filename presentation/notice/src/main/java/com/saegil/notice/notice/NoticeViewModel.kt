package com.saegil.notice.notice

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
import com.saegil.notice.notice.NoticeUiState.*
import com.saegil.domain.model.Notice
import com.saegil.domain.usecase.GetFeedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

@HiltViewModel
class NoticeViewModel @Inject constructor(
    getFeedUseCase: GetFeedUseCase
) : ViewModel() {

    val organization = MutableStateFlow<Long?>(null)
    val query = MutableStateFlow("")

    val feedUiState: StateFlow<NoticeUiState> =
        combine(organization, query) { organization, query ->
            getFeedUseCase(
                organization = organization,
                query = query.ifBlank { null }
            )
        }.map<Flow<PagingData<Notice>>, NoticeUiState>(NoticeUiState::Success)
            .onStart { emit(Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Loading,
            )

    fun setSourceFilter(filteredOrganization: Int?) {
        organization.value = filteredOrganization?.toLong()
    }

    fun onSearchTriggered(searchValue: String) {
        query.value = searchValue
    }
}