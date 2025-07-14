package com.saegil.ai_conversation.aiconversationlist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.GetRealTimeTokenUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AiConversationListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRealtimeTokenUseCase: GetRealTimeTokenUsecase

) : ViewModel() {

    private val _stateFlow: MutableStateFlow<AiConversationListState> =
        MutableStateFlow(AiConversationListState())

    val stateFlow: StateFlow<AiConversationListState> = _stateFlow.asStateFlow()


    fun onRequestToken() {
        Log.d("AiConversationListViewModel", "onRequestToken called")
        viewModelScope.launch {
            val result = getRealtimeTokenUseCase()
            Log.d("result", "${result}")
        }
    }

}

class AiConversationListState