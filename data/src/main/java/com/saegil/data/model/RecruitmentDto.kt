package com.saegil.data.model

import com.saegil.domain.model.Recruitment
import kotlinx.serialization.Serializable

@Serializable
data class RecruitmentDto(
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
) {
    fun toDomain() : Recruitment {
        return Recruitment(
            id = id,
            name = name,
            workPlaceName = workPlaceName,
            workTime = workTime,
            pay = pay,
            recruitmentPeriod = recruitmentPeriod,
            webLink = webLink,
            address = address,
            latitude = latitude,
            longitude = longitude,
            distanceMeters = distanceMeters
        )
    }
}
