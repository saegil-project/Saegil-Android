package com.saegil.data.remote

import com.saegil.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.headers
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class RealTimeServiceImpl (
    private val client: HttpClient
) : RealTimeService {

    override suspend fun connectToRealtimeSession(secret: String) {
        try {
            client.webSocket(
                request = {
                    url("wss://api.openai.com/v1/realtime?model=gpt-4o-realtime-preview-2024-12-17")
//                    header(HttpHeaders.Authorization, "Bearer ${BuildConfig.OPEN_AI_API_KEY}")
                    header(HttpHeaders.Authorization, "Bearer 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")
                    header("OpenAI-Beta", "realtime=v1")
                }
            ) {
                println("✅ WebSocket connected")

                val initMessage = buildJsonObject {
                    put("type", "start")
                }
                send(Frame.Text(initMessage.toString()))

                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        println("📨 Received: ${frame.readText()}")
                    }
                }
            }
        } catch (e: Exception) {
            println("❌ WebSocket error: ${e.message}")
            e.printStackTrace()
        }
    }


}
