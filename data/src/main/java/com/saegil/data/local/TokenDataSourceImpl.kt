package com.saegil.data.local

import com.saegil.data.model.TokenEntity
import javax.inject.Inject

class TokenDataSourceImpl @Inject constructor(
    private val tokenDao: TokenDao
) : TokenDataSource {

    override suspend fun saveAccessToken(token: String) {
        val tokenEntity = TokenEntity(accessToken = token, refreshToken = null)
        tokenDao.insertToken(tokenEntity)
    }

    override suspend fun saveRefreshToken(token: String) {
        val tokenEntity = TokenEntity(accessToken = null, refreshToken = token)
        tokenDao.insertToken(tokenEntity)
    }

    override suspend fun getAccessToken(): String? {
        return tokenDao.getToken()?.accessToken
    }

    override suspend fun getRefreshToken(): String? {
        return tokenDao.getToken()?.refreshToken
    }
}