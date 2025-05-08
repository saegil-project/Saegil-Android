package com.saegil.domain.model

data class SimulationLogDetail(
    val scenarioName: String,
    val messages: List<SimulationMessage>
)
