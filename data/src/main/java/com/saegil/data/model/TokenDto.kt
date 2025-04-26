package com.saegil.data.model

import com.saegil.domain.model.Token

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
) {
    fun toDomain() = Token(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}
