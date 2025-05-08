package com.saegil.domain.usecase

import com.saegil.domain.model.SimulationLog
import com.saegil.domain.repository.SimulationLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSimulationLogUseCase @Inject constructor(
    private val simulationLogRepository: SimulationLogRepository
) {
    operator fun invoke(): Flow<List<SimulationLog>?> =
        simulationLogRepository.getSimulationLogs().map { it.ifEmpty { null } }
}