package com.saegil.data.model

import com.saegil.domain.model.SimulationLog
import kotlinx.serialization.Serializable

@Serializable
data class SimulationLogDto(
    val id: Long,
    val scenarioName: String,
    val scenarioIconImageUrl: String,
    val createdAt: String
) {
    fun toDomain() = SimulationLog(
        id = id,
        scenarioName = scenarioName,
        scenarioIconImageUrl = scenarioIconImageUrl,
        createdAt = createdAt
    )
}
