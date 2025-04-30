package com.saegil.data.model;

import com.saegil.domain.model.UploadAudio
import kotlinx.serialization.Serializable;

@Serializable
data class UploadAudioDto(
    val response: String,
    val threadId: String,
    val text: String
) {
    fun toDomain(): UploadAudio {
        return UploadAudio(
            response = response,
            threadId = threadId,
            text = text
        )
    }
}
