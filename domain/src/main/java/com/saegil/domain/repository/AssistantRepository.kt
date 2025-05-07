package com.saegil.domain.repository

import com.saegil.domain.model.UploadAudio
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AssistantRepository {
    suspend fun uploadAudio(
        file: File,
    ): Flow<Result<UploadAudio>>
}
