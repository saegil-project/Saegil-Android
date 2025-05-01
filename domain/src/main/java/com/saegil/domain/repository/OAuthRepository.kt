package com.saegil.domain.repository

import com.saegil.domain.model.Token

interface OAuthRepository {
    suspend fun loginWithKakao(accessToken: String): Boolean
    suspend fun getToken(): Token
    suspend fun validateAccessToken(accessToken: String): Boolean
    suspend fun requestLogout(refreshToken: String): Boolean
    suspend fun requestWithdrawal(refreshToken: String): Boolean
}