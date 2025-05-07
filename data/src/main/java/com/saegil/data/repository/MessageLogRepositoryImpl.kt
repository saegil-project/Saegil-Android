package com.saegil.data.repository

import com.saegil.data.remote.MessageLogService
import com.saegil.domain.model.SimulationLogDetail
import com.saegil.domain.repository.MessageLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MessageLogRepositoryImpl @Inject constructor(
    private val messageLogService: MessageLogService
): MessageLogRepository {

    override fun getMessages(id: Long): Flow<SimulationLogDetail> = flow {
        emit(messageLogService.getMessages(id).toDomain())
    }

}