package com.saegil.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidateTokenDto(
    val validated: Boolean
)