package com.saegil.domain.repository

import kotlinx.coroutines.flow.Flow

interface OAuthRepository {
    suspend fun loginWithKakao(kakaoAccessToken: String): Flow<Boolean>
}