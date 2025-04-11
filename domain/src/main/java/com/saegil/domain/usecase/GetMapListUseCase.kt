package com.saegil.domain.usecase

import com.saegil.domain.model.Organization
import com.saegil.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMapListUseCase @Inject constructor(
    private val mapRepository: MapRepository
) {
    operator fun invoke(
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
    ): Flow<List<Organization>> =
        mapRepository.getNearByOriganizations(latitude, longitude, radius)
}