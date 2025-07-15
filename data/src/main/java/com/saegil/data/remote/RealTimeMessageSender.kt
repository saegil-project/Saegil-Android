package com.saegil.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.websocket.Frame
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject
import java.util.Base64

object RealtimeMessageSender {

    suspend fun sendUserTextMessage(
        client: DefaultClientWebSocketSession,
        message: String
    ) {
        val createMessage = buildJsonObject {
            put("type", "conversation.item.create")
            putJsonObject("item") {
                put("type", "message")
                put("role", "user")
                putJsonArray("content") {
                    addJsonObject {
                        put("type", "input_text")
                        put("text", message)
                    }
                }
            }
        }

        val createResponse = buildJsonObject {
            put("type", "response.create")
        }

        client.send(Frame.Text(createMessage.toString()))
        client.send(Frame.Text(createResponse.toString()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun sendPcmAudio(client: DefaultClientWebSocketSession, pcm: ByteArray) {
        val base64Audio = Base64.getEncoder().encodeToString(pcm)
        val appendFrame = buildJsonObject {
            put("type", "input_audio_buffer.append")
            put("audio", base64Audio)
        }
        client.send(Frame.Text(appendFrame.toString()))
    }

    suspend fun commitAudio(client: DefaultClientWebSocketSession) {
        val commitFrame = buildJsonObject {
            put("type", "input_audio_buffer.commit")
        }
        client.send(Frame.Text(commitFrame.toString()))
    }
}
