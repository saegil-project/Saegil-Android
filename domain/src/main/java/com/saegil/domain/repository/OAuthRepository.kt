package com.saegil.domain.repository

import com.saegil.domain.model.TokenEntity


interface OAuthRepository {
    suspend fun loginWithKakao(accessToken: String): Boolean
    suspend fun getToken(): TokenEntity?
    suspend fun validateAccessToken(accessToken: String): Boolean
}