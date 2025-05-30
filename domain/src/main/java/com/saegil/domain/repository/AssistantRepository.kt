package com.saegil.domain.repository

import com.saegil.domain.model.UploadAudio
import java.io.File

interface AssistantRepository {
    suspend fun uploadAudio(
        file: File,
    ): UploadAudio
}
