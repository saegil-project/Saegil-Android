package com.saegil.domain.repository

import com.saegil.domain.model.SimulationLog
import kotlinx.coroutines.flow.Flow

interface SimulationLogRepository {

    fun getSimulationLogs(): Flow<List<SimulationLog>>

}