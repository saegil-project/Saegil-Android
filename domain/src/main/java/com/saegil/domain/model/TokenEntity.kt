package com.saegil.domain.model

data class TokenEntity(
    val id: Int = 0,
    val accessToken: String?,
    val refreshToken: String?
)
