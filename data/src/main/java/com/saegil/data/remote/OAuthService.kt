package com.saegil.data.remote

import com.example.app.data.proto.TokenProto
import com.saegil.data.model.ValidateTokenDto

interface OAuthService {
    suspend fun loginWithKakao(accessToken: String): TokenProto
    suspend fun validateAccessToken(accessToken: String): ValidateTokenDto
    suspend fun requestLogout(refreshToken: String): Boolean
    suspend fun requestWithdrawal(refreshToken: String): Boolean
}