package com.saegil.domain.model

data class Organization(
    val id: Long,
    val name: String,
    val businessName: Business,
    val latitude: Double,
    val longitude: Double,
    val operatingHours: String,
    val telephoneNumber: String,
    val address: String,
    val distance: Double,
)
