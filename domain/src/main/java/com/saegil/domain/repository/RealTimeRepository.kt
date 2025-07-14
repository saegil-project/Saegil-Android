package com.saegil.domain.repository

interface RealTimeRepository {
    suspend fun connect(secret: String)
}
