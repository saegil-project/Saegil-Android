package com.saegil.map.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.GetMapListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMapListUseCase: GetMapListUseCase,
) : ViewModel() {

    private val _latitude = MutableStateFlow(0.0)
    private val _longitude = MutableStateFlow(0.0)
    private val _radius = MutableStateFlow(500) //500, 1000, 5000 라서 500m를 디폴트로 함

    val selectedTab = MutableStateFlow<Long>(0)

    fun setTabFilter(idx: Int) {
        selectedTab.value = idx.toLong()
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
        _radius
    ) { lat, lng, radius ->
        Timber.d("Location changed, fetching new data: lat=$lat, lng=$lng, radius=$radius")
        getMapListUseCase(lat, lng, radius)
    }.flatMapLatest { organizationsFlow ->
        organizationsFlow.map { organizations ->
            MapUiState.Success(organizations)
        }
    }.onStart {
        MapUiState.Loading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MapUiState.Loading,
    )
}