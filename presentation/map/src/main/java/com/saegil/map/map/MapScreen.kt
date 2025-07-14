package com.saegil.map.map

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.saegil.core.common.Markers
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.component.SaegilTabButton
import com.saegil.domain.model.Organization
import com.saegil.domain.model.Recruitment
import com.saegil.map.map.MapConstants.EARTH_RADIUS_KM
import com.saegil.map.map.MapConstants.THRESHOLD
import com.saegil.map.map.components.OrganizationBottomSheet
import com.saegil.map.map.components.RecruitmentBottomSheet
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
    var selectedRecruitment by remember { mutableStateOf<Recruitment?>(null) }
    var lastLocation by remember { mutableStateOf<LatLng?>(null) }
    var lastZoomLevel by remember { mutableStateOf<Double?>(null) }
    val selectedIndex by viewModel.selectedTab.collectAsStateWithLifecycle()

    LaunchedEffect(cameraPositionState.position) {
        val currentLocation = LatLng(
            cameraPositionState.position.target.latitude,
            cameraPositionState.position.target.longitude
        )
        val currentZoomLevel = cameraPositionState.position.zoom

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

        if (lastZoomLevel != currentZoomLevel) {
            viewModel.updateZoomLevel(currentZoomLevel)
        }
    }

    MapScreen(
        mapState = mapState,
        cameraPositionState = cameraPositionState,
        selectedOrganization = selectedOrganization,
        selectedRecruitment = selectedRecruitment,
        onOrganizationSelected = { selectedOrganization = it; selectedRecruitment = null },
        onRecruitmentSelected = { selectedRecruitment = it; selectedOrganization = null },
        onDismissBottomSheet = {
            selectedOrganization = null
            selectedRecruitment = null
        },
        onLocationUpdate = viewModel::updateLocation,
        onTabSelect = viewModel::setTabFilter,
        selectedIndex = selectedIndex.toInt(),
        modifier = modifier
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
internal fun MapScreen(
    mapState: MapUiState,
    cameraPositionState: CameraPositionState,
    selectedOrganization: Organization?,
    selectedRecruitment: Recruitment?,
    onOrganizationSelected: (Organization) -> Unit,
    onRecruitmentSelected: (Recruitment) -> Unit,
    onDismissBottomSheet: () -> Unit,
    onLocationUpdate: (Double, Double) -> Unit,
    onTabSelect: (Int) -> Unit,
    selectedIndex: Int,
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
                "근처 복지시설/일자리 찾기",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val filter = listOf("복지시설", "채용 정보")
                filter.forEachIndexed { index, text ->
                    SaegilTabButton(
                        text = text,
                        isSelected = selectedIndex == index,
                        onClick = { onTabSelect(index) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            NaverMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    locationTrackingMode = LocationTrackingMode.Follow,
                )
            ) {
                when (mapState) {
                    is MapUiState.OrganizationSuccess -> {
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
                                    },
                                    markers = organization.businessName
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
                                    },
                                    markers = organization.businessName
                                )
                            }
                        }
                    }

                    is MapUiState.RecruitmentSuccess -> {
                        mapState.recruitmentList.forEach { recruitment ->
                            if (recruitment != selectedRecruitment) {
                                UnselectedMarker(
                                    position = LatLng(recruitment.latitude, recruitment.longitude),
                                    captionText = recruitment.name,
                                    markers = Markers.RECRUITMENT,
                                    onClick = {
                                        onRecruitmentSelected(recruitment)
                                        true
                                    },
                                )
                            } else {
                                SelectedMarker(
                                    position = LatLng(recruitment.latitude, recruitment.longitude),
                                    captionText = recruitment.name,
                                    markers = Markers.RECRUITMENT,
                                    onClick = {
                                        onRecruitmentSelected(recruitment)
                                        true
                                    },
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

    selectedRecruitment?.let { recruitment ->
        RecruitmentBottomSheet(
            recruitment = recruitment,
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
    const val THRESHOLD = 0.1
}

@Composable
@Preview(name = "Map")
private fun MapScreenPreview() {
    MapScreen()
}

