package com.saegil.domain.repository


import com.saegil.domain.model.Organization
import com.saegil.domain.model.Scenario
import kotlinx.coroutines.flow.Flow

interface ScenarioRepository {

    fun getScenarios(
    ): Flow<List<Scenario>>

}