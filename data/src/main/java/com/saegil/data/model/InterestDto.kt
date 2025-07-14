package com.saegil.data.model

import com.saegil.domain.model.Interest
import kotlinx.serialization.Serializable

@Serializable
data class InterestDto(
    val type: String,
    val name: String,
) {
    fun toDomain() = Interest(
        type = type,
        name = name,
    )
}
