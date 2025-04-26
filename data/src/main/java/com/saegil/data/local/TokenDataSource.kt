package com.saegil.data.local

import com.saegil.data.model.TokenDto

interface TokenDataSource {
    suspend fun saveToken(tokenDto: TokenDto)
    suspend fun getToken(): TokenDto
    suspend fun clearTokens()
}