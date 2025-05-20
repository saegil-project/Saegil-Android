package com.saegil.data.repository

import com.saegil.data.local.ThreadPreferencesManager
import com.saegil.data.remote.AssistantService
import com.saegil.domain.model.UploadAudio
import com.saegil.domain.repository.AssistantRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class AssistantRepositoryImpl(
    private val assistantService: AssistantService,
    private val threadPreferencesManager: ThreadPreferencesManager

) : AssistantRepository {

    override suspend fun uploadAudio(file: File): UploadAudio = assistantService.getAssistant(file).toDomain()

    override suspend fun saveThreadId(threadId: String) {
        threadPreferencesManager.saveThreadId(threadId)
    }

    override fun getThreadId(): Flow<String?> {
        return threadPreferencesManager.threadId
    }

    override suspend fun clearThreadId() {
        threadPreferencesManager.clearThreadId()
    }
}
