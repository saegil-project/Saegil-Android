package com.saegil.map.map

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.saegil.map.R

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

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
internal fun MapScreen(
    state: UiState<MapState>,
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = viewModel(),
) {
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val cameraPositionState = rememberCameraPositionState()

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted) {
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        location?.let {
                            val cameraUpdate = CameraUpdate.scrollTo(
                                LatLng(it.latitude, it.longitude)
                            ).animate(CameraAnimation.Easing)
                            cameraPositionState.move(cameraUpdate)

                            // 위치 정보를 얻으면 ViewModel의 함수 호출
                            viewModel.getNearbyOrganizations(
                                latitude = it.latitude,
                                longitude = it.longitude,
                                radius = 500  // 반경 500m (필요에 따라 조정)
                            )
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e("MapScreen", "Error getting location", exception)
                    }
            } catch (e: Exception) {
                Log.e("MapScreen", "Error getting location", e)
            }
        } else {
            permissionState.launchPermissionRequest()
        }
    }



    NaverMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            locationTrackingMode = LocationTrackingMode.Follow,
        )
    ) {
        when (state) {
            is UiState.Success -> {
                when (val data = state.data) {
                    is MapState.MapList -> {
                        data.organizations.forEach { organization ->
                            CustomMarker(
                                position = LatLng(
                                    organization.latitude,
                                    organization.longitude
                                ),
                                captionText = organization.name,
                                onClick = { true }
                            )
                        }
                    }

                    null -> {}
                }
            }
            else -> {}
        }
    }
}


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun CustomMarker(
    position: LatLng,
    captionText: String,
    onClick: () -> Boolean
) {
    Marker(
        state = MarkerState(position = position),
        icon = OverlayImage.fromResource(R.drawable.ic_map_pin), // 커스텀 마커 이미지
        width = 30.dp,  // 마커 너비
        height = 30.dp, // 마커 높이
        captionText = captionText,
        captionColor = Color.Black,
        captionHaloColor = Color.White,
        onClick = { onClick() }
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

