package com.saegil.ai_conversation.aiconversation

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.EndRealtimeChatUseCase
import com.saegil.domain.usecase.GetRealTimeTokenUsecase
import com.saegil.domain.usecase.StartRealtimeChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AiConversationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val startRealtimeChatUseCase: StartRealtimeChatUseCase,
    private val getRealtimeTokenUseCase: GetRealTimeTokenUsecase,
    private val disconnectRealtimeUseCase: EndRealtimeChatUseCase

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
    }


    fun stopChatSession() {
        viewModelScope.launch {
            disconnectRealtimeUseCase()
        }
    }

    private var audioRecord: AudioRecord? = null
    private var isRecording = false

    fun startRecordingAndSendAudio() {
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT,
            BUFFER_SIZE
        )

        isRecording = true
        audioRecord?.startRecording()

        viewModelScope.launch(Dispatchers.IO) {
            val buffer = ByteArray(BUFFER_SIZE)

            while (isRecording) {
                val readBytes = audioRecord?.read(buffer, 0, buffer.size) ?: 0
                if (readBytes > 0) {
                    val pcmData = buffer.copyOf(readBytes)
                    startRealtimeChatUseCase.sendPcm(pcmData)
                }
            }

            // 커밋
            startRealtimeChatUseCase.commitAudio()
        }
    }

    fun stopRecording() {
        isRecording = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }

companion object{
    private const val SAMPLE_RATE = 16000 // 16kHz
    private const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO // 모노 채널
    private const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT // 16bit signed PCM
    private val BUFFER_SIZE = AudioRecord.getMinBufferSize(
        SAMPLE_RATE,
        CHANNEL_CONFIG,
        AUDIO_FORMAT
    ).coerceAtLeast(SAMPLE_RATE * 2) // 1초 분량 or 최소값 이상

}
}



class AiConversationState

