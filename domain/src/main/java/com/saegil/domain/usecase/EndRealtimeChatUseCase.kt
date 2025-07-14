package com.saegil.domain.usecase

import com.saegil.domain.repository.RealTimeRepository
import javax.inject.Inject

class EndRealtimeChatUseCase @Inject constructor(
    private val repository: RealTimeRepository
) {
    suspend operator fun invoke() = repository.disconnect()
}