package com.saegil.domain.usecase

import androidx.paging.PagingData
import com.saegil.domain.model.Scenario
import com.saegil.domain.repository.ScenarioLogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScenarioLogUseCase @Inject constructor(
    scenarioLogRepository: ScenarioLogRepository
) {
    //operator fun invoke() : Flow<PagingData<Scenario>> =
}