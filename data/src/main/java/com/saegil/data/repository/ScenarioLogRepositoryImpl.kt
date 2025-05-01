package com.saegil.data.repository

import androidx.paging.PagingData
import com.saegil.domain.model.Scenario
import com.saegil.domain.repository.ScenarioLogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScenarioLogRepositoryImpl @Inject constructor(

): ScenarioLogRepository {

    override fun getScenarioLogs(): Flow<PagingData<Scenario>> {
        TODO("Not yet implemented")
    }

    override fun setScenarioLog(scenario: Scenario) {
        TODO("Not yet implemented")
    }

}