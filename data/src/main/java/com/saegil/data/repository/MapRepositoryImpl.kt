package com.saegil.data.repository

import com.saegil.data.remote.MapService
import com.saegil.domain.model.Organization
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

}