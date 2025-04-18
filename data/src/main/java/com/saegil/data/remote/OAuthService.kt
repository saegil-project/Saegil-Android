package com.saegil.data.remote

import com.saegil.data.model.TokenEntityDto
import com.saegil.data.model.ValidateTokenResponse

interface OAuthService {
    suspend fun loginWithKakao(authCode: String): TokenEntityDto
    suspend fun validateAccessToken(accessToken: String): ValidateTokenResponse
}