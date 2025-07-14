package com.saegil.data.remote

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi
import com.saegil.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RealTimeServiceImpl(
    private val client: HttpClient
) : RealTimeService {

    private var session: DefaultClientWebSocketSession? = null
    private var isStreaming = false

    override suspend fun connectToRealtimeSession(secret: String) {
        try {
            client.webSocket(
                method = HttpMethod.Get,
                host = "api.openai.com",
                path = "/v1/realtime/sessions/$secret",
                request = {
                    url("wss://api.openai.com/v1/realtime?model=gpt-4o-realtime-preview-2024-12-17&modalities=audio")
//                    header(HttpHeaders.Authorization, "Bearer ${BuildConfig.OPEN_AI_API_KEY}")
                    header(HttpHeaders.Authorization, "Bearer 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")
                    header("OpenAI-Beta", "realtime=v1")
                }
            ) {
                println("✅ WebSocket connected")
                session = this
                isStreaming = true

                launch(Dispatchers.IO) {
                    startAudioStreaming(this@webSocket)
                }

                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        println("📨 Received: ${frame.readText()}")
                    }
                }
            }
        } catch (e: Exception) {
            println("❌ WebSocket error: ${e.message}")
        } finally {
            isStreaming = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun sendPcm(pcm: ByteArray) {
        session?.let {
            RealtimeMessageSender.sendPcmAudio(it, pcm)
        } ?: println("❌ No active WebSocket session to send PCM")
    }

    override suspend fun commitAudio() {
        session?.let {
            RealtimeMessageSender.commitAudio(it)
        } ?: println("❌ No active WebSocket session to commit audio")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun startAudioStreaming(ws: DefaultClientWebSocketSession) {
        val recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT,
            BUFFER_SIZE
        )

        if (recorder.state != AudioRecord.STATE_INITIALIZED) {
            println("❌ AudioRecord 초기화 실패")
            return
        }

        val buffer = ByteArray(BUFFER_SIZE)
        var appendCount = 0

        recorder.startRecording()
        println("🎙️ Audio recording started")

        while (isStreaming) {
            val read = recorder.read(buffer, 0, buffer.size)
            if (read > 0) {
                val audioChunk = buffer.copyOf(read)

                CoroutineScope(Dispatchers.IO).launch {
                    RealtimeMessageSender.sendPcmAudio(ws, audioChunk)
                }

                appendCount++
            } else {
                println("⚠️ Audio read failed or empty (read=$read)")
            }

            if (appendCount >= 10) {
                CoroutineScope(Dispatchers.IO).launch {
                    RealtimeMessageSender.commitAudio(ws)
                }
                appendCount = 0
            }
        }

        recorder.stop()
        recorder.release()

        CoroutineScope(Dispatchers.IO).launch {
            RealtimeMessageSender.commitAudio(ws)
        }

        println("🎙️ Audio recording stopped and committed")
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

    override suspend fun disconnect() {
        isStreaming = false
        session?.close()
        session = null
        println("🔌 WebSocket disconnected")
    }

}
