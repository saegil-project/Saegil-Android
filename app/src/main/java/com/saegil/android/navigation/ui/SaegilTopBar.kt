package com.saegil.android.navigation.ui

import androidx.compose.foundation.Image
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.saegil.android.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaegilTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.ic_saegil_logo),
                contentDescription = "앱 로고",
                )
        },
        modifier = Modifier
    )
}