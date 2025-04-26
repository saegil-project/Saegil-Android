package com.saegil.data.remote

import com.saegil.data.model.TokenDto
import com.saegil.data.model.ValidateTokenResponse

interface OAuthService {
    suspend fun loginWithKakao(accessToken: String): TokenDto
    suspend fun validateAccessToken(accessToken: String): ValidateTokenResponse
}