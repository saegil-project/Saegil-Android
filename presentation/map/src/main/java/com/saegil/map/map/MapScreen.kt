package com.saegil.map.map

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
//            isLocationButtonEnabled = true
        )
    ) {
        when (state) {
            is UiState.Success -> {
                when (val data = state.data) {
                    is MapState.MapList -> {
                        data.organizations.forEach { organization ->
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

                    null -> {}
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

