package com.saegil.domain.usecase

import com.saegil.domain.model.Organization
import com.saegil.domain.model.Scenario
import com.saegil.domain.repository.MapRepository
import com.saegil.domain.repository.ScenarioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScenarioListUseCase @Inject constructor(
    private val scenarioRepository: ScenarioRepository
) {
    operator fun invoke(): Flow<List<Scenario>> = scenarioRepository.getScenarios()
}