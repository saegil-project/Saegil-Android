package com.saegil.domain.usecase

import com.saegil.domain.repository.AssistantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThreadIdUseCase @Inject constructor(
    private val assistantRepository: AssistantRepository
) {
    suspend operator fun invoke(): Flow<String?> {
        return assistantRepository.getThreadId()
    }
} 