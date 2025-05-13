package com.saegil.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class TextToSpeechServiceImpl @Inject constructor(
    private val client: HttpClient
) : TextToSpeechService {

    override suspend fun getAssistantAudio(text: String): File = withContext(Dispatchers.IO) {
        File.createTempFile("assistant_response", ".mp3").apply {
            writeBytes(client.post(HttpRoutes.TTS) {
                accept(ContentType.Application.OctetStream)
                setBody(mapOf("text" to text, "provider" to "OPENAI"))
            }.body())
        }
    }

}