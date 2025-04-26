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
import com.saegil.map.map.components.OrganizationBottomSheet
import com.saegil.map.map.components.SelectedMarker
import com.saegil.map.map.components.UnselectedMarker
import timber.log.Timber

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val mapState by viewModel.mapUiState.collectAsStateWithLifecycle()
    val cameraPositionState = rememberCameraPositionState()
    var selectedOrganization by remember { mutableStateOf<Organization?>(null) }

    LaunchedEffect(cameraPositionState.position) {
        viewModel.updateLocation(
            cameraPositionState.position.target.latitude,
            cameraPositionState.position.target.longitude
        )
        viewModel.updateZoomLevel(cameraPositionState.position.zoom)
    }

    MapScreen(
        mapState = mapState,
        cameraPositionState = cameraPositionState,
        selectedOrganization = selectedOrganization,
        onOrganizationSelected = { organization -> selectedOrganization = organization },
        onDismissBottomSheet = { selectedOrganization = null },
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
                        Timber.e(exception, "Error getting location")
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error getting location")
            }
        } else {
            permissionState.launchPermissionRequest()
        }
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

@Composable
@Preview(name = "Map")
private fun MapScreenPreview() {
    MapScreen()
}

