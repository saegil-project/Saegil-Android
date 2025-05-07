package com.saegil.data.remote

import com.saegil.data.model.SimulationLogDto
import com.saegil.data.remote.HttpRoutes.SIMULATION_LOG
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class SimulationLogServiceImpl @Inject constructor(
    private val client: HttpClient
) : SimulationLogService {

    override suspend fun getSimulationLogList(): List<SimulationLogDto> {
        return client.get(SIMULATION_LOG).body()
    }

}