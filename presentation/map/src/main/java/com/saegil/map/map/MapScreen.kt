package com.saegil.map.map

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.domain.model.Organization
import com.saegil.map.map.MapConstants.EARTH_RADIUS_KM
import com.saegil.map.map.MapConstants.THRESHOLD
import com.saegil.map.map.components.OrganizationBottomSheet
import com.saegil.map.map.components.SelectedMarker
import com.saegil.map.map.components.UnselectedMarker
import timber.log.Timber
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val mapState by viewModel.mapUiState.collectAsStateWithLifecycle()
    val cameraPositionState = rememberCameraPositionState()
    var selectedOrganization by remember { mutableStateOf<Organization?>(null) }
    var lastLocation by remember { mutableStateOf<LatLng?>(null) }


    LaunchedEffect(cameraPositionState.position) {
        val currentLocation = LatLng(
            cameraPositionState.position.target.latitude,
            cameraPositionState.position.target.longitude
        )

        if (lastLocation == null || calculateDistance(
                lastLocation!!,
                currentLocation
            ) > THRESHOLD
        ) {
            viewModel.updateLocation(
                currentLocation.latitude,
                currentLocation.longitude
            )
            viewModel.updateZoomLevel(cameraPositionState.position.zoom)
            lastLocation = currentLocation
        }
    }

    MapScreen(
        mapState = mapState,
        cameraPositionState = cameraPositionState,
        selectedOrganization = selectedOrganization,
        onOrganizationSelected = { organization -> selectedOrganization = organization },
        onDismissBottomSheet = { selectedOrganization = null },
        onLocationUpdate = viewModel::updateLocation,
        modifier = modifier
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
internal fun MapScreen(
    mapState: MapUiState,
    cameraPositionState: CameraPositionState,
    selectedOrganization: Organization?,
    onOrganizationSelected: (Organization) -> Unit,
    onDismissBottomSheet: () -> Unit,
    onLocationUpdate: (Double, Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(permissionState.status.isGranted) {
        Timber.d("Location permission status: ${permissionState.status.isGranted}")
        if (permissionState.status.isGranted) {
            try {
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    null
                ).addOnSuccessListener { location ->
                    location?.let {
                        Timber.d("Got current location: lat=${it.latitude}, lng=${it.longitude}")
                        val cameraUpdate = CameraUpdate.scrollTo(
                            LatLng(it.latitude, it.longitude)
                        ).animate(CameraAnimation.Easing)
                        cameraPositionState.move(cameraUpdate)
                    } ?: run {
                        Timber.e("Current location is null")
                    }
                }.addOnFailureListener { exception ->
                    Timber.e(exception, "Error getting current location")
                }
            } catch (e: Exception) {
                Timber.e(e, "Error getting current location")
            }
        } else {
            Timber.d("Requesting location permission")
            permissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(cameraPositionState.position) {
        Timber.d("Camera position updated: lat=${cameraPositionState.position.target.latitude}, lng=${cameraPositionState.position.target.longitude}")
        onLocationUpdate(
            cameraPositionState.position.target.latitude,
            cameraPositionState.position.target.longitude
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column {
            SaegilTitleText(
                "지도",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            NaverMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    locationTrackingMode = LocationTrackingMode.Follow,
                )
            ) {
                when (mapState) {
                    is MapUiState.Success -> {
                        mapState.organizationList.forEach { organization ->
                            if (organization != selectedOrganization) {
                                UnselectedMarker(
                                    position = LatLng(
                                        organization.latitude,
                                        organization.longitude
                                    ),
                                    captionText = organization.name,
                                    onClick = {
                                        onOrganizationSelected(organization)
                                        true
                                    }
                                )
                            } else {
                                SelectedMarker(
                                    position = LatLng(
                                        organization.latitude,
                                        organization.longitude
                                    ),
                                    captionText = organization.name,
                                    onClick = {
                                        Timber.d("Selected marker: ${organization.name} ${organization.address}")
                                        onOrganizationSelected(organization)
                                        true
                                    }
                                )
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    selectedOrganization?.let { organization ->
        Timber.d("selectedOrganization: ${organization.name} ${organization.address}")

        OrganizationBottomSheet(
            organization = organization,
            onDismiss = onDismissBottomSheet
        )
    }
}

private fun calculateDistance(location1: LatLng, location2: LatLng): Double {
    val lat1 = Math.toRadians(location1.latitude)
    val lat2 = Math.toRadians(location2.latitude)
    val dLat = Math.toRadians(location2.latitude - location1.latitude)
    val dLon = Math.toRadians(location2.longitude - location1.longitude)

    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(lat1) * cos(lat2) *
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return EARTH_RADIUS_KM * c
}

object MapConstants {
    const val EARTH_RADIUS_KM = 6371.0
    const val ZOOM_LEVEL = 15.0
    const val THRESHOLD = 0.1
}

@Composable
@Preview(name = "Map")
private fun MapScreenPreview() {
    MapScreen()
}

