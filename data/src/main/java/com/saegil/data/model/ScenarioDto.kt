package com.saegil.data.model

import com.saegil.domain.model.Scenario
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioDto(
    val id: Long,
    val name: String,
    val iconImageUrl: String,
) {
    fun toDomain(): Scenario {
        return Scenario(
            id = id,
            name = name,
            iconImageUrl = iconImageUrl
        )
    }
}
