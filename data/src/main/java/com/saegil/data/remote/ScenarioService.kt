package com.saegil.data.remote

import com.saegil.data.model.ScenarioDto

interface ScenarioService {

    suspend fun getScenarios(): List<ScenarioDto>?
}