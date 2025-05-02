package com.saegil.domain.model

data class SimulationMessage(
    val id: Long,
    val createdAt: String,
    val isMyMessage: Boolean,
    val contents: String
)
