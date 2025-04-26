package com.saegil.data.repository

import com.saegil.data.local.TokenDao
import com.saegil.data.remote.OAuthService
import com.saegil.domain.model.TokenEntity
import com.saegil.domain.repository.OAuthRepository
import javax.inject.Inject

class OAuthRepositoryImpl @Inject constructor(
    private val oAuthService: OAuthService,
    private val tokenDao: TokenDao
) : OAuthRepository {

    override suspend fun loginWithKakao(authCode: String): Boolean {
        return try {
            val response = oAuthService.loginWithKakao(authCode)
            tokenDao.insertToken(response)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getToken(): TokenEntity? {
        return tokenDao.getToken()?.toDomain()
    }

    override suspend fun validateAccessToken(accessToken: String): Boolean {
        return try {
            oAuthService.validateAccessToken(accessToken).validated
        } catch (e: Exception) {
            false
        }
    }
}
