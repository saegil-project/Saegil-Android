package com.saegil.data.repository

import com.saegil.data.remote.SimulationLogService
import com.saegil.domain.model.SimulationLogDetail
import com.saegil.domain.repository.SimulationLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SimulationLogRepositoryImpl @Inject constructor(
    private val simulationLogService: SimulationLogService
): SimulationLogRepository {

    override fun getSimulationLogs() = flow {
        emit(simulationLogService.getSimulationLogList().map { it.toDomain() })
    }

    override fun getMessages(id: Long): Flow<SimulationLogDetail> = flow {
        emit(simulationLogService.getMessages(id).toDomain())
    }

}