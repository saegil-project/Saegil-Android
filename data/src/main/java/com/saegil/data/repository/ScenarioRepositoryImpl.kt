package com.saegil.data.repository

import com.saegil.data.remote.ScenarioService
import com.saegil.domain.model.Scenario
import com.saegil.domain.repository.ScenarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ScenarioRepositoryImpl @Inject constructor(
    private val scenarioService: ScenarioService
) : ScenarioRepository {

    override fun getScenarios(): Flow<List<Scenario>> = flow {
        scenarioService.getScenarios()?.let {
            emit(it.map { dto -> dto.toDomain() })
        }
    }.flowOn(Dispatchers.IO) //데이터 요청은 IO

}