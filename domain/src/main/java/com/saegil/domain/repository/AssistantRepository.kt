package com.saegil.domain.repository

import com.saegil.domain.model.UploadAudio
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AssistantRepository {
    suspend fun uploadAudio(
        file: File,
    ): UploadAudio

    suspend fun saveThreadId(threadId: String)
    fun getThreadId(): Flow<String?>
    suspend fun clearThreadId()
}
