package com.saegil.data.repository

import android.util.Log
import com.saegil.data.remote.MapService
import com.saegil.domain.model.Organization
import com.saegil.domain.model.Recruitment
import com.saegil.domain.repository.MapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapService: MapService
) : MapRepository {

    override fun getNearbyOrganizations(
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
    ): Flow<List<Organization>> = flow {
        mapService.getNearbyOrganizations(
            latitude,
            longitude,
            radius
        )?.let {
            emit(it.map { dto -> dto.toDomain() })
        }
    }.flowOn(Dispatchers.IO) //데이터 요청은 IO

    override fun getRecruitmentList(
        latitude: Double?,
        longitude: Double?,
        radius: Int?
    ): Flow<List<Recruitment>> = flow {
        Log.d("경로", "레포")
        mapService.getNearbyRecruitments(
            latitude,
            longitude,
            radius
        )?.let {
            emit(it.map { dto -> dto.toDomain() })
        }
    }.flowOn(Dispatchers.IO)

}