package com.saegil.domain.model

import com.saegil.core.common.Markers

data class Organization(
    val id: Long,
    val name: String,
    val businessName: Markers,
    val latitude: Double,
    val longitude: Double,
    val telephoneNumber: String,
    val address: String,
    val distance: Double,
)
