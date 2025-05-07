package com.saegil.data.remote

import com.saegil.data.model.SimulationLogDetailDto

interface MessageLogService {

    suspend fun getMessages(simulationId: Long): SimulationLogDetailDto

}