package com.saegil.data.repository

import com.saegil.data.remote.RealTimeService
import com.saegil.domain.repository.RealTimeRepository

class RealTimeRepositoryImpl (
    private val service: RealTimeService
) : RealTimeRepository {

    override suspend fun connect(secret: String) {
        service.connectToRealtimeSession(secret)
    }

    override suspend fun sendPcm(pcm: ByteArray) {
        service.sendPcm(pcm)
    }

    override suspend fun commitAudio() {
        service.commitAudio()
    }

    override suspend fun disconnect() {
        service.disconnect()
    }
}
