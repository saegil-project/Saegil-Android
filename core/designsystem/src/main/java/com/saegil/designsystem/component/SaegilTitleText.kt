package com.saegil.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.theme.h1

@Composable
fun SaegilTitleText(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .padding(top = 20.dp)
    )
}