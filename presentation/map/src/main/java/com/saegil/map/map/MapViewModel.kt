package com.saegil.map.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.GetMapListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMapListUseCase: GetMapListUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<MapState>> = MutableStateFlow(UiState.Init)
    val uiState: StateFlow<UiState<MapState>> = _uiState.asStateFlow()


    fun getNearbyOrganizations(
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
    ) {
        viewModelScope.launch {
            getMapListUseCase(
                latitude,
                longitude,
                radius
            ).collect {
                _uiState.value = UiState.Success(MapState.MapList(it))
//                _mapState.value = _mapState.value
            }
        }
    }
}


//    val mapUiState: StateFlow<MapUiState> =
//        getMapListUseCase(
//            37.5326, 126.8469, 1000
//        )
//            .map<List<Organization>, MapUiState>(MapUiState::Success)
////            .onStart { emit(Loading) }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                initialValue = MapUiState.Loading,
//            )

