package com.saegil.data.remote

import com.saegil.data.model.SimulationLogDetailDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import javax.inject.Inject

class MessageLogServiceImpl @Inject constructor(
    private val client: HttpClient
) : MessageLogService {

    override suspend fun getMessages(simulationId: Long): SimulationLogDetailDto {
        val urlBuilder = URLBuilder(HttpRoutes.SIMULATION_LOG).apply {
            parameters.append("simulationId", simulationId.toString())
            parameters.append("messages", "messages")
        }
        return client.get(urlBuilder.build()).body()
    }

}