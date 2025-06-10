package com.saegil.domain.model

import com.saegil.core.common.Business

data class Organization(
    val id: Long,
    val name: String,
    val businessName: Business,
    val latitude: Double,
    val longitude: Double,
    val telephoneNumber: String,
    val address: String,
    val distance: Double,
)
