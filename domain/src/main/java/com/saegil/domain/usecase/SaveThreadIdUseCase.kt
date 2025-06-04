package com.saegil.domain.usecase

import com.saegil.domain.repository.AssistantRepository
import javax.inject.Inject

class SaveThreadIdUseCase @Inject constructor(
    private val assistantRepository: AssistantRepository
) {
    suspend operator fun invoke(threadId: String) {
        assistantRepository.saveThreadId(threadId)
    }
} 