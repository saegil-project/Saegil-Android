package com.saegil.data.remote

import com.saegil.data.model.OrganizationDto

interface MapService {

    suspend fun getNearByOriganizations(
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
    ): List<OrganizationDto>?

}