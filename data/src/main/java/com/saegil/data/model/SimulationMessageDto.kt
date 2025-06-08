package com.saegil.data.model

import com.saegil.domain.model.SimulationMessage
import kotlinx.serialization.Serializable

@Serializable
data class SimulationMessageDto(
    val id: Long,
    val isFromUser: Boolean,
    val contents: String,
    val createdAt: String
) {
    fun toDomain() = SimulationMessage(
        id = id,
        isFromUser = isFromUser,
        contents = contents,
        createdAt = createdAt
    )
}
