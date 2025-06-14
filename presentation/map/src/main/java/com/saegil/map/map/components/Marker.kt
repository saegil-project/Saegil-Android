package com.saegil.map.map.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.overlay.OverlayImage
import com.saegil.core.common.Markers
import com.saegil.designsystem.R

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun SelectedMarker(
    position: LatLng,
    captionText: String,
    onClick: () -> Boolean,
    markers: Markers? = Markers.OTHERS
) {
    val iconRes = when (markers) {
        Markers.CHILDREN_WELFARE -> R.drawable.ic_children_welfare_selected
        Markers.ELDERLY_WELFARE -> R.drawable.ic_elderly_welfare_selected
        Markers.DISABILITY_WELFARE -> R.drawable.ic_disability_welfare_selected
        Markers.WOMEN_FAMILY_WELFARE -> R.drawable.ic_women_family_welfare_selected
        Markers.MEDICAL_WELFARE_EQUIPMENT -> R.drawable.ic_medical_welfare_equipment_selected
        Markers.OTHERS, null -> R.drawable.ic_map_pin_selected
        Markers.RECRUITMENT -> R.drawable.marker_recruitment_selected
    }

    Marker(
        state = MarkerState(position = position),
        icon = OverlayImage.fromResource(iconRes),
        width = 60.dp,
        height = 66.dp,
        captionText = captionText,
        captionColor = MaterialTheme.colorScheme.secondary,
        captionHaloColor = Color.White,
        onClick = { onClick() }
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun UnselectedMarker(
    position: LatLng,
    captionText: String,
    onClick: () -> Boolean,
    markers: Markers? = Markers.OTHERS
) {
    val iconRes = when (markers) {
        Markers.CHILDREN_WELFARE -> R.drawable.ic_children_welfare
        Markers.ELDERLY_WELFARE -> R.drawable.ic_elderly_welfare
        Markers.DISABILITY_WELFARE -> R.drawable.ic_disability_welfare
        Markers.WOMEN_FAMILY_WELFARE -> R.drawable.ic_women_family_welfare
        Markers.MEDICAL_WELFARE_EQUIPMENT -> R.drawable.ic_medical_welfare_equipment
        Markers.OTHERS, null -> R.drawable.ic_map_pin
        Markers.RECRUITMENT -> R.drawable.marker_recruitment_unselected
    }

    Marker(
        state = MarkerState(position = position),
        icon = OverlayImage.fromResource(iconRes),
        width = 30.dp,
        height = 30.dp,
        captionText = captionText,
        captionColor = MaterialTheme.colorScheme.primary,
        captionHaloColor = Color.White,
        onClick = { onClick() }
    )
}

@Preview
@Composable
fun MarkerPreview() {
    SelectedMarker(
        position = LatLng(37.5665, 126.9780),
        captionText = "우리집",
        onClick = { true },
        markers = Markers.ELDERLY_WELFARE
    )

    UnselectedMarker(
        position = LatLng(37.5665, 126.9780),
        captionText = "우리집",
        onClick = { true },
        markers = Markers.ELDERLY_WELFARE
    )
}
