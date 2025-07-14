package com.saegil.domain.usecase

import com.saegil.domain.repository.RealTimeRepository
import javax.inject.Inject

class StartRealtimeChatUseCase @Inject constructor(
    private val repository: RealTimeRepository
) {
    suspend operator fun invoke(clientSecret: String) {
        repository.connect(clientSecret)
    }


    suspend fun sendPcm(pcm: ByteArray) {
        repository.sendPcm(pcm)
    }

    suspend fun commitAudio() {
        repository.commitAudio()
    }
}
