package com.saegil.ai_conversation.aiconversationend

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AiConversationEndViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<AiConversationEndState> =
        MutableStateFlow(AiConversationEndState())

    val stateFlow: StateFlow<AiConversationEndState> = _stateFlow.asStateFlow()


}

class AiConversationEndState