package com.saegil.map.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.model.Organization
import com.saegil.domain.usecase.GetMapListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    getMapListUseCase: GetMapListUseCase,
) : ViewModel() {

    //    private val _mapState: MutableStateFlow<MapState> = MutableStateFlow(MapState())
//    val mapState: StateFlow<MapState> = _mapState.asStateFlow()
//
//    private val _actionsFlow: MutableStateFlow<MapActions> = MutableStateFlow(MapActions())
//    val actionsFlow: StateFlow<MapActions> = _actionsFlow.asStateFlow()
//
//    suspend fun getNearByOriganizations(
//        latitude: Double?,
//        longitude: Double?,
//        radius: Int?,
//    ) {
//
//        getMapListUseCase(
//            latitude,
//            longitude,
//            radius
//        ).collect{
//            _mapState.value = _mapState.value
//
//        }
//
//    }
    val mapUiState: StateFlow<MapUiState> =
        getMapListUseCase(
            37.5326, 126.8469, 1000
        )
            .map<List<Organization>, MapUiState>(MapUiState::Success)
//            .onStart { emit(Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MapUiState.Loading,
            )

}

class MapActions {


}

sealed interface MapUiState {

    data object Loading : MapUiState

    data class Success(
        val orignizations: List<Organization>
    ) : MapUiState

}