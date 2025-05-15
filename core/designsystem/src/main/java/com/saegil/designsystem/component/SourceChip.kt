package com.saegil.designsystem.component

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.theme.SaegilAndroidTheme

@Composable
fun SourceChip(
    title: String,
    index: Int?,
    selected: Boolean,
    onFilterChipClick: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    FilterChip(
        onClick = {
            onFilterChipClick(index)
        },
        label = {
            Text(
                text = title,
                modifier = Modifier.graphicsLayer {
                    translationY = (-1.5).dp.toPx()
                },
            )
        },
        selected = selected,
        modifier = modifier,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.secondary,
            containerColor = MaterialTheme.colorScheme.surface,
            selectedLabelColor = MaterialTheme.colorScheme.onSecondary,
            labelColor = MaterialTheme.colorScheme.secondary,
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = MaterialTheme.colorScheme.secondary,
        )
    )
}


@Preview(apiLevel = 33, device = Devices.NEXUS_5)
@Composable
fun SourceChipPreview() {
    SaegilAndroidTheme {
        SourceChip(
            title = "전체",
            index = null,
            selected = true,
            onFilterChipClick = {}
        )
    }
}