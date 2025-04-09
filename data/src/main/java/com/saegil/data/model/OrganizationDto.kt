package com.saegil.data.model

import com.saegil.domain.model.Organization
import kotlinx.serialization.Serializable

@Serializable
data class OrganizationDto(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val operatingHours: String,
    val telephoneNumber: String,
    val address: String,
    val distance: Double,
) {
    fun toDomain(): Organization {
        return Organization(
            id = id,
            name = name,
            latitude = latitude,
            longitude = longitude,
            operatingHours = operatingHours,
            telephoneNumber = telephoneNumber,
            address = address,
            distance = distance
        )
    }
}