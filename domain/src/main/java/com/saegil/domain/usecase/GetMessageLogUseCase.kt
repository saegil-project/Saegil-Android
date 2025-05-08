package com.saegil.domain.usecase

import com.saegil.domain.repository.SimulationLogRepository
import javax.inject.Inject

class GetMessageLogUseCase @Inject constructor(
    private val simulationLogRepository: SimulationLogRepository
){
    operator fun invoke(id: Long) = simulationLogRepository.getMessages(id)
}