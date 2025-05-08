package com.saegil.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.R
import com.saegil.designsystem.theme.SaegilAndroidTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaegilTopBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .windowInsetsPadding(TopAppBarDefaults.windowInsets)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .size(70.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_saegil_logo),
            contentDescription = "앱 로고",
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(54.dp)
        )
    }
}


@Preview(apiLevel = 33)
@Composable
fun LogoTopBarPreview() {
    SaegilAndroidTheme {
        Column {
            SaegilTopBar(
                modifier = Modifier
            )
        }
    }
}