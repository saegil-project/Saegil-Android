package com.saegil.data.repository

import com.saegil.data.remote.SimulationLogService
import com.saegil.domain.repository.SimulationLogRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SimulationLogRepositoryImpl @Inject constructor(
    private val simulationLogService: SimulationLogService
): SimulationLogRepository {

    override fun getSimulationLogs() = flow {
        emit(simulationLogService.getSimulationLogList().map { it.toDomain() })
    }

}