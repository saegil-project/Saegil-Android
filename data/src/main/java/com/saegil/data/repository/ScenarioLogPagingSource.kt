package com.saegil.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saegil.data.remote.ScenarioLogService
import com.saegil.domain.model.Scenario

class ScenarioLogPagingSource(
    private val scenarioLogService: ScenarioLogService,
): PagingSource<Long, Scenario>() {
    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Scenario> {
        val lastId = params.key
        return try {
            val response = scenarioLogService.getScenarioLogList(
                //어떻게 가져올 지 모르겠으니 일단 비움 명세나오고 몇개 가져올건지 등을 하면 될듯
            )
            if (response == null) {
                LoadResult.Error(Exception(""))
            } else {
//                val scenarioLogs = response.scenarios.map { it.toDomain() } // 여기도
                val nextKey = if (response.hasNext && scenarioLogs.isNotEmpty()) {
                    scenarioLogs.last().id
                } else {
                    null
                }

                LoadResult.Page(
                    data = scenarioLogs,
                    prevKey = null,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, Scenario>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}