package com.saegil.data.repository

import com.saegil.data.remote.OAuthService
import com.saegil.domain.model.Token
import com.saegil.domain.repository.OAuthRepository
import javax.inject.Inject

class OAuthRepositoryImpl @Inject constructor(
    private val oAuthService: OAuthService,
) : OAuthRepository {

    override suspend fun loginWithKakao(accessToken: String): Boolean {
        return try {
            val response = oAuthService.loginWithKakao(accessToken)
            //tokenDao.insertToken(response)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getToken(): Token? {
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
