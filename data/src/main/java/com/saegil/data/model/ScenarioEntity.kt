package com.saegil.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saegil.domain.model.Scenario

@Entity(tableName = "scenario")
data class ScenarioEntity(
    @PrimaryKey val id: Long,
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
