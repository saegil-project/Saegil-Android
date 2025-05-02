package com.saegil.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saegil.data.remote.ScenarioLogService
import com.saegil.domain.model.Scenario
import com.saegil.domain.repository.ScenarioLogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ScenarioLogRepositoryImpl @Inject constructor(
    private val scenarioLogService: ScenarioLogService
): ScenarioLogRepository {

    override fun getScenarioLogs(): Flow<PagingData<Scenario>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                ScenarioLogPagingSource(
                    scenarioLogService = scenarioLogService
                )
            }
        ).flow.flowOn(Dispatchers.IO)
    }

}