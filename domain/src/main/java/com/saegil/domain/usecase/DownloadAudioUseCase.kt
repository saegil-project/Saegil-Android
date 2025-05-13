package com.saegil.domain.usecase

import com.saegil.domain.repository.TextToSpeechRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class DownloadAudioUseCase @Inject constructor(
    private val textToSpeechRepository: TextToSpeechRepository
){

    suspend operator fun invoke(text: String): Flow<File> = textToSpeechRepository.downloadAudio(text)

}