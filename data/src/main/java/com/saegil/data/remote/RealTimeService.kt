package com.saegil.data.remote

interface RealTimeService {
    suspend fun connectToRealtimeSession(secret: String)

    suspend fun sendPcm(pcm: ByteArray)

    suspend fun commitAudio()

    suspend fun disconnect()
}
