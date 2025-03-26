package com.saegil.announcement.announcement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<AnnouncementState> =
        MutableStateFlow(AnnouncementState())

    val stateFlow: StateFlow<AnnouncementState> = _stateFlow.asStateFlow()


}

class AnnouncementState