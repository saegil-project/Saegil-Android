package com.saegil.domain.repository

import com.saegil.domain.model.SimulationLog
import com.saegil.domain.model.SimulationLogDetail
import kotlinx.coroutines.flow.Flow

interface SimulationLogRepository {

    fun getSimulationLogs(): Flow<List<SimulationLog>>

    fun getMessages(id: Long): Flow<SimulationLogDetail>

}