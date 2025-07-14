package com.saegil.ai_conversation.aiconversation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AiConversationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<AiConversationState> =
        MutableStateFlow(AiConversationState())

    val stateFlow: StateFlow<AiConversationState> = _stateFlow.asStateFlow()


}

class AiConversationState