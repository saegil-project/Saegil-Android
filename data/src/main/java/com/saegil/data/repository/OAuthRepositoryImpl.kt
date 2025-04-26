package com.saegil.data.repository

import com.saegil.data.local.TokenDataSource
import com.saegil.data.remote.OAuthService
import com.saegil.domain.model.Token
import com.saegil.domain.repository.OAuthRepository
import javax.inject.Inject

class OAuthRepositoryImpl @Inject constructor(
    private val oAuthService: OAuthService,
    private val tokenDataSource: TokenDataSource
) : OAuthRepository {

    override suspend fun loginWithKakao(accessToken: String): Boolean {
        return try {
            val response = oAuthService.loginWithKakao(accessToken)
            tokenDataSource.saveToken(response)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getToken(): Token {
        return tokenDataSource.getToken().toDomain()
    }

    override suspend fun validateAccessToken(accessToken: String): Boolean {
        return try {
            oAuthService.validateAccessToken(accessToken).validated
        } catch (e: Exception) {
            false
        }
    }
}
