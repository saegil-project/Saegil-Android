package com.saegil.data.model

import com.saegil.domain.model.SimulationLogDetail
import kotlinx.serialization.Serializable

@Serializable
data class SimulationLogDetailDto(
    val scenarioName: String,
    val messages: List<SimulationMessageDto>
) {
    fun toDomain() = SimulationLogDetail(
        scenarioName = scenarioName,
        messages = messages.map { it.toDomain() }
    )
}
