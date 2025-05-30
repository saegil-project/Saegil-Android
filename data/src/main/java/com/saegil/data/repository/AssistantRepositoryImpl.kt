package com.saegil.data.repository

import com.saegil.data.remote.AssistantService
import com.saegil.domain.model.UploadAudio
import com.saegil.domain.repository.AssistantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class AssistantRepositoryImpl(
    private val assistantService: AssistantService
) : AssistantRepository {

    override suspend fun uploadAudio(file: File): Flow<UploadAudio> = flow<UploadAudio> {
        assistantService.getAssistant(file).toDomain()
    }.flowOn(Dispatchers.IO)
}
