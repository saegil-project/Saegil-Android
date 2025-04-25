package com.saegil.data.repository

import android.util.Log
import com.saegil.data.local.TokenDao
import com.saegil.data.model.TokenEntityDto
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
            Log.d("경로", "레포지터리")
            val response = oAuthService.loginWithKakao(authCode)
            Log.d("경로", "서비스")
            tokenDao.insertToken(response)
            Log.d("테스트토큰", "나오나")
            true
        } catch (e: Exception) {
            Log.d("테스트토큰", e.toString())
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
