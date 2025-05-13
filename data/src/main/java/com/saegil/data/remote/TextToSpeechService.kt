package com.saegil.data.remote

import java.io.File

interface TextToSpeechService {
    suspend fun getAssistantAudio(text: String): File
}