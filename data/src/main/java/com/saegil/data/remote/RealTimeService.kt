package com.saegil.data.remote

interface RealTimeService {
    suspend fun connectToRealtimeSession(secret: String)
}
