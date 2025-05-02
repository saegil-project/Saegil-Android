package com.saegil.domain.repository

import androidx.paging.PagingData
import com.saegil.domain.model.Scenario
import kotlinx.coroutines.flow.Flow

interface ScenarioLogRepository {

    fun getScenarioLogs(): Flow<PagingData<Scenario>>

}