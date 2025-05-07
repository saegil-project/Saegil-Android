package com.saegil.domain.usecase

import com.saegil.domain.repository.MessageLogRepository
import javax.inject.Inject

class GetMessageLogUseCase @Inject constructor(
    private val messageLogRepository: MessageLogRepository
){
    operator fun invoke(id: Long) = messageLogRepository.getMessages(id)
}