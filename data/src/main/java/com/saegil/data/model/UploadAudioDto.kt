package com.saegil.data.model;

import com.saegil.domain.model.UploadAudio
import kotlinx.serialization.Serializable

@Serializable
data class UploadAudioDto(
    val question: String,
    val response: String,
    val threadId: String,
) {
    fun toDomain(): UploadAudio {
        return UploadAudio(
            question = question,
            response = response,
            threadId = threadId,
        )
    }
}
