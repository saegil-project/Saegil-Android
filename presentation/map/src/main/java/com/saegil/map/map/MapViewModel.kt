package com.saegil.map.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.model.Organization
import com.saegil.domain.usecase.GetMapListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMapListUseCase: GetMapListUseCase,
) : ViewModel() {

    var latitude: Double by mutableDoubleStateOf(0.0)
        private set

    var longitude: Double by mutableDoubleStateOf(0.0)
        private set

    var zoomLevel: Double by mutableDoubleStateOf(15.0)
        private set

    var radius: Int by mutableStateOf(0)

    fun updateLocation(lat: Double, lng: Double) {
        latitude = lat
        longitude = lng
    }

    fun updateZoomLevel(zoom: Double) {
        zoomLevel = zoom
    }

    val mapUiState: StateFlow<MapUiState> =
        getMapListUseCase(latitude, longitude, radius)
            .map<List<Organization>, MapUiState>(MapUiState::Success)
            .onStart { emit(MapUiState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MapUiState.Loading,
            )
}