package com.saegil.data.remote

import com.saegil.data.model.UploadAudioDto
import java.io.File

interface AssistantService {

    suspend fun getAssistant(file: File, threadId: String?, scenarioId: Int): UploadAudioDto

}