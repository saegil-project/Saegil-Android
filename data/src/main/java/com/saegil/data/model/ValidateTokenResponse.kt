package com.saegil.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidateTokenResponse(
    val validated: Boolean
)