package com.saegil.domain.repository


import com.saegil.domain.model.Organization
import com.saegil.domain.model.Recruitment
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    fun getNearbyOrganizations(
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
    ): Flow<List<Organization>>

    fun getRecruitmentList(
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
    ): Flow<List<Recruitment>>

}