package com.saegil.map.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel(),
) {

    val mapState by viewModel.uiState.collectAsStateWithLifecycle()

    MapScreen(
        mapState,
        modifier
    )

}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun MapScreen(
    state: UiState<MapState>,
    modifier: Modifier = Modifier,
) {
    NaverMap(
        modifier = modifier.fillMaxSize(),
//        cameraPositionState = cameraPositionState,
//        locationTrackingMode = LocationTrackingMode.Follow,  // 현재 위치 추적 모드
        properties = MapProperties(
//            locationButtonEnabled = true  // 현재 위치 버튼 활성화
        )
    ) {
        when (state) {
            is UiState.Success -> {
                when (val mapState = state.data) {
                    is MapState.MapList -> {
                        mapState.organizations.forEach { organization ->
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        organization.latitude,
                                        organization.longitude
                                    )
                                ),
                                captionText = organization.name,
                                onClick = {
                                    true
                                }
                            )
                        }
                    }

                    else -> {}
                }
            }

            else -> {}
        }
    }
}


@Composable
@Preview(name = "Map")
private fun MapScreenPreview() {
    MapScreen(
//        state = MapState(),
//        actions = MapActions()
    )
}

