package com.saegil.domain.usecase

import com.saegil.domain.model.UploadAudio
import com.saegil.domain.repository.AssistantRepository
import java.io.File
import javax.inject.Inject

class UploadAudioUseCase @Inject constructor(
    private val assistantRepository: AssistantRepository
) {
    suspend operator fun invoke(file: File): UploadAudio = assistantRepository.uploadAudio(file)
} 