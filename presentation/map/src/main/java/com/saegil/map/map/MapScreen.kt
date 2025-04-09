package com.saegil.map.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel(),
) {

    val mapState by viewModel.mapUiState.collectAsStateWithLifecycle()

    MapScreen(
        mapState,
        modifier
    )

    when (mapState) {
        MapUiState.Loading -> {}
        is MapUiState.Success -> {

        }
    }


//    viewModel.getNearByOriganizations( 37.5326, 126.8469,1000)

}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun MapScreen(
    state: MapUiState,
//    actions: MapActions,
    modifier: Modifier = Modifier,
) {
    NaverMap(
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
@Preview(name = "Map")
private fun MapScreenPreview() {
    MapScreen(
//        state = MapState(),
//        actions = MapActions()
    )
}

