package com.saegil.designsystem.component

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
            Text(title)
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


