package com.saegil.ai_conversation.aiconversation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.GetRealTimeTokenUsecase
import com.saegil.domain.usecase.StartRealtimeChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AiConversationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val startRealtimeChatUseCase: StartRealtimeChatUseCase,
    private val getRealtimeTokenUseCase: GetRealTimeTokenUsecase

) : ViewModel() {

    private val _stateFlow: MutableStateFlow<AiConversationState> =
        MutableStateFlow(AiConversationState())

    val stateFlow: StateFlow<AiConversationState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val token = onRequestToken()
            startChatSession(token)
        }
    }
    suspend fun onRequestToken(): String {
        Log.d("AiConversationListViewModel", "onRequestToken called")
        val result = getRealtimeTokenUseCase()
        Log.d("result", result)
        return result
    }

    fun startChatSession(secret: String) {
        viewModelScope.launch {
            startRealtimeChatUseCase(secret)
        }
    }}

class AiConversationState