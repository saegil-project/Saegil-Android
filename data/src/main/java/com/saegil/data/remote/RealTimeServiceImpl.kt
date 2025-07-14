package com.saegil.data.remote

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioTrack
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
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.util.Base64

class RealTimeServiceImpl(
    private val client: HttpClient
) : RealTimeService {

    private var session: DefaultClientWebSocketSession? = null
    private var isStreaming = false

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun connectToRealtimeSession(secret: String) {
        try {
            client.webSocket(
                method = HttpMethod.Get,
                host = "api.openai.com",
                path = "/v1/realtime/sessions/$secret",
                request = {
                    url("wss://api.openai.com/v1/realtime?model=gpt-4o-realtime-preview-2024-12-17&modalities=audio")
                    header(HttpHeaders.Authorization, "Bearer ${BuildConfig.}")
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
                         val json = frame.readText()
                        println("📨 Received: $json")

                        val jsonObj = Json.parseToJsonElement(json).jsonObject
                        when (jsonObj["type"]?.jsonPrimitive?.content) {
                            "session.created" -> {
                                // Handle session creation confirmation.
                                // You might want to store the session ID here.
                                val sessionId = jsonObj["session"]?.jsonObject?.get("id")?.jsonPrimitive?.content
                                println("Session created with ID: $sessionId")
                            }
                            // This is the crucial part: handling the audio delta messages
                            "response.audio.delta" -> {
                                // Extract the "delta" field which contains the Base64 encoded audio bytes
                                val base64Audio = jsonObj["delta"]?.jsonPrimitive?.content ?: ""
                                if (base64Audio.isNotEmpty()) {
                                    try {
                                        // Decode the Base64 string to a ByteArray
                                        val audioBytes = Base64.getDecoder().decode(base64Audio)
                                        // Write the audio bytes to the AudioTrack for playback
                                        playAudio(audioBytes)
                                    } catch (e: IllegalArgumentException) {
                                        println("❌ Base64 decoding error: ${e.message}")
                                    }
                                }
                            }
                            "response.completed" -> {
                                // The AI's response has completed.
                                // You might want to signal the end of speech here,
                                // but keep the AudioTrack playing until its buffer is empty.
                                println("AI response completed.")
                            }
                            // Handle other message types if necessary (e.g., input_audio_buffer.speech_started/stopped)
                            else -> {
                                // println("Received message of type: ${jsonObj["type"]?.jsonPrimitive?.content}")
                            }
//                            "audio" -> {
//                                val base64Audio = jsonObj["data"]?.jsonPrimitive?.content ?: ""
//                                val audioBytes = Base64.getDecoder().decode(base64Audio)
//                                playAudio(audioBytes)
//                            }
//                            else -> {
//                                // 다른 메시지 타입 처리 (예: session.created, error 등)
////                                println("Received message but not audio: $json")
//                            }
                        }
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

    private var audioTrack: AudioTrack? = null
    private var isAudioPlaying = false
    private val audioQueue: ArrayDeque<ByteArray> = ArrayDeque()

    private fun initializeAudioTrackIfNeeded() {
        if (audioTrack == null) {
            audioTrack = AudioTrack(
                AudioManager.STREAM_MUSIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                BUFFER_SIZE,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE
            ).apply {
                play()
                println("🔄 AudioTrack initialized")
            }
        }
    }

    private fun playAudio(audio: ByteArray) {
        audioQueue.addLast(audio)
        println("📥 Queued audio chunk. Queue size: ${audioQueue.size}")

        if (!isAudioPlaying) {
            isAudioPlaying = true
            CoroutineScope(Dispatchers.IO).launch {
                initializeAudioTrackIfNeeded()

                while (audioQueue.isNotEmpty()) {
                    val chunk = audioQueue.removeFirst()
                    audioTrack?.write(chunk, 0, chunk.size)
                }

                // wait for buffer to play
                audioTrack?.flush()
                isAudioPlaying = false
                println("✅ Finished playing all queued audio.")
            }
        }
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
