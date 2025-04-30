package com.saegil.data.remote

import com.example.app.data.proto.TokenProto
import com.saegil.data.model.ValidateTokenResponse

interface OAuthService {
    suspend fun loginWithKakao(accessToken: String): TokenProto
    suspend fun validateAccessToken(accessToken: String): ValidateTokenResponse
    suspend fun requestLogout(accessToken: String, refreshToken: String): Boolean
    suspend fun requestWithdrawal(accessToken: String, refreshToken: String): Boolean
}