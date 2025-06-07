package com.saegil.map.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.GetMapListUseCase
import com.saegil.domain.usecase.GetRecruitmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMapListUseCase: GetMapListUseCase,
    private val getRecruitmentUseCase: GetRecruitmentUseCase,
) : ViewModel() {

    private val _latitude = MutableStateFlow(0.0)
    private val _longitude = MutableStateFlow(0.0)
    private val _radius = MutableStateFlow(500) //500, 1000, 5000 라서 500m를 디폴트로 함

    val selectedTab = MutableStateFlow<Long>(0)

    fun setTabFilter(idx: Int) {
        selectedTab.value = idx.toLong()
        Timber.d("Selected tab: $idx")
    }

    fun updateLocation(lat: Double, lng: Double) {
        Timber.d("Updating location in ViewModel: lat=$lat, lng=$lng")
        _latitude.value = lat
        _longitude.value = lng
    }

    fun updateZoomLevel(zoom: Double) {
        Timber.d("Updating zoom level: $zoom")

        val radius = when {
            zoom >= 13.5 -> 500    // 500m 반경
            zoom >= 11.5 -> 1000    // 1km 반경
            else -> 5000            // 5km 반경
        }
        _radius.value = radius
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val mapUiState: StateFlow<MapUiState> = combine(
        _latitude,
        _longitude,
        _radius,
        selectedTab
    ) { lat, lng, radius, tab ->
        Timber.d("Location or tab changed: lat=$lat, lng=$lng, radius=$radius, tab=$tab")
        Quadruple(lat, lng, radius, tab)
    }.flatMapLatest { (lat, lng, radius, tab) ->
        Timber.d("경로: lat=$lat, lng=$lng, radius=$radius, tab=$tab")
        when (tab) {
            0L -> getMapListUseCase(lat, lng, radius).map {
                MapUiState.OrganizationSuccess(it)
            }

            1L -> getRecruitmentUseCase(lat, lng, radius).map {
                Timber.d("경로: $it")
                MapUiState.RecruitmentSuccess(it)
            }

            else -> flowOf(MapUiState.Loading)
        }
    }.onStart {
        emit(MapUiState.Loading)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MapUiState.Loading,
    )
}

data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

