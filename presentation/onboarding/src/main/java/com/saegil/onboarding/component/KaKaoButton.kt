package com.saegil.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.onboarding.R

@Composable
fun KaKaoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.ic_kakao_login),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick),
        contentDescription = "kakao login icon",
    )
}


@Preview
@Composable
fun KakaoButtonPreview() {
    KaKaoButton(
        onClick = {}
    )
}

