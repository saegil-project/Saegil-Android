package com.saegil.data.model

import com.saegil.domain.model.Organization
import com.saegil.core.common.Markers
import kotlinx.serialization.Serializable

@Serializable
data class OrganizationDto(
    val id: Long,
    val name: String,
    val markersName: Markers,
    val latitude: Double,
    val longitude: Double,
    val telephoneNumber: String,
    val address: String,
    val distance: Double,
) {
    fun toDomain(): Organization {
        return Organization(
            id = id,
            name = name,
            markersName = markersName,
            latitude = latitude,
            longitude = longitude,
            telephoneNumber = telephoneNumber,
            address = address,
            distance = distance
        )
    }
}