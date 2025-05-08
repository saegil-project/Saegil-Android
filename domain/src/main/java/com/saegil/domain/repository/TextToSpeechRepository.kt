package com.saegil.domain.repository

import kotlinx.coroutines.flow.Flow
import java.io.File

interface TextToSpeechRepository {

    suspend fun downloadAudio(
        text: String
    ): Flow<File>

}