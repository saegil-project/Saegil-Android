package com.saegil.domain.repository

interface RealTimeRepository {
    suspend fun connect(secret: String)

    suspend fun sendPcm(pcm: ByteArray)

    suspend fun commitAudio()

    suspend fun disconnect()

}
