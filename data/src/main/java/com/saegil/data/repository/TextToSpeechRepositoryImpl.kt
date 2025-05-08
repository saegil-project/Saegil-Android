package com.saegil.data.repository

import com.saegil.data.remote.TextToSpeechService
import com.saegil.domain.repository.TextToSpeechRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject

class TextToSpeechRepositoryImpl @Inject constructor(
    private val textToSpeechService: TextToSpeechService
): TextToSpeechRepository {

    override suspend fun downloadAudio(text: String): Flow<File> = flow {
        emit(textToSpeechService.getAssistantAudio(text))
    }.flowOn(Dispatchers.IO)

}