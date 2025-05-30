package com.saegil.domain.usecase

import com.saegil.domain.model.UploadAudio
import com.saegil.domain.repository.AssistantRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class UploadAudioUseCase @Inject constructor(
    private val assistantRepository: AssistantRepository
) {
    suspend operator fun invoke(file: File): Flow<UploadAudio> {
        return assistantRepository.uploadAudio(file)
    }
} 