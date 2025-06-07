package com.saegil.data.remote

import com.saegil.data.model.OrganizationDto
import com.saegil.data.model.RecruitmentDto

interface MapService {

    suspend fun getNearbyOrganizations(
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
    ): List<OrganizationDto>?

    suspend fun getNearbyRecruitments(
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
    ): List<RecruitmentDto>?

}