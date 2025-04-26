package com.saegil.data.remote

import com.saegil.data.model.TokenEntityDto
import com.saegil.data.model.ValidateTokenResponse

interface OAuthService {
    suspend fun loginWithKakao(accessToken: String): TokenEntityDto
    suspend fun validateAccessToken(accessToken: String): ValidateTokenResponse
}