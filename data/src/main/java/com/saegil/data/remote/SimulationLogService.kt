package com.saegil.data.remote

import com.saegil.data.model.SimulationLogDetailDto
import com.saegil.data.model.SimulationLogDto

interface SimulationLogService {
    suspend fun getSimulationLogList(): List<SimulationLogDto>
    suspend fun getMessages(simulationId: Long): SimulationLogDetailDto
}