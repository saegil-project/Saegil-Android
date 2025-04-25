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
import com.saegil.designsystem.R


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun SelectedMarker(
    position: LatLng,
    captionText: String,
    onClick: () -> Boolean
) {
    Marker(
        state = MarkerState(position = position),
        icon = OverlayImage.fromResource(R.drawable.ic_map_pin_selected),
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
    onClick: () -> Boolean
) {
    Marker(
        state = MarkerState(position = position),
        icon = OverlayImage.fromResource(R.drawable.ic_map_pin),
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
        onClick = { true }
    )

    UnselectedMarker(
        position = LatLng(37.5665, 126.9780),
        captionText = "우리집",
        onClick = { true }
    )
}
