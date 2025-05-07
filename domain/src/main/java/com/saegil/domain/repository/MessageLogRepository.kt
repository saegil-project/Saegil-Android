package com.saegil.domain.repository

import com.saegil.domain.model.SimulationLogDetail
import kotlinx.coroutines.flow.Flow

interface MessageLogRepository {

    fun getMessages(id: Long): Flow<SimulationLogDetail>

}