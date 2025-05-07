package com.saegil.domain.model

data class SimulationMessage(
    val id: Long,
    val isFromUser: Boolean,
    val contents: String,
    val createdAt: String
)
