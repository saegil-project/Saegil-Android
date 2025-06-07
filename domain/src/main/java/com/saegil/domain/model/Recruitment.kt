package com.saegil.domain.model

data class Recruitment(
    val id: Long,
    val name: String,
    val workPlaceName: String,
    val workTime: String,
    val pay: String,
    val recruitmentPeriod: String,
    val webLink: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val distanceMeters: Double
)
