package com.saegil.data.local

import com.example.app.data.proto.TokenProto

interface TokenDataSource {
    suspend fun saveToken(tokenProto: TokenProto)
    suspend fun getToken(): TokenProto
    suspend fun clearToken()
    suspend fun getDeviceToken(): String
}