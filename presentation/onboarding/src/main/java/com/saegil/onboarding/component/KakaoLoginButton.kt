package com.saegil.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.onboarding.R

@Composable
fun KakaoLoginButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_kakao_login),
            modifier = Modifier
                .width(300.dp)
                .height(45.dp)
                .clickable(onClick = onClick),
            contentDescription = "kakao login icon",
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview(device = Devices.NEXUS_5)
@Composable
fun KakaoLoginButtonPreview() {
    KakaoLoginButton(
        onClick = {}
    )
}

