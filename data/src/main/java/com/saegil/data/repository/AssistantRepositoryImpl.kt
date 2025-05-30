package com.saegil.data.repository

import com.saegil.data.remote.AssistantService
import com.saegil.domain.model.UploadAudio
import com.saegil.domain.repository.AssistantRepository
import java.io.File

class AssistantRepositoryImpl(
    private val assistantService: AssistantService
) : AssistantRepository {

    override suspend fun uploadAudio(file: File): UploadAudio = assistantService.getAssistant(file).toDomain()

}
