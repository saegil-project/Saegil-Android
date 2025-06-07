package com.saegil.domain.usecase

import android.util.Log
import com.saegil.domain.model.Recruitment
import com.saegil.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecruitmentUseCase @Inject constructor(
    private val mapRepository: MapRepository
) {
    operator fun invoke(
        latitude: Double?,
        longitude: Double?,
        radius: Int?
    ): Flow<List<Recruitment>> {
        Log.d("경로", "유케")
        return mapRepository.getRecruitmentList(latitude, longitude, radius)
    }
}