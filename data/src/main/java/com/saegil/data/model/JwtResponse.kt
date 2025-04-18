package com.saegil.data.model

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)