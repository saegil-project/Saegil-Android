package com.saegil.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.saegil.onboarding.R

@Composable
fun OnBoardingBackground(
    index: Int,
    modifier: Modifier
) {
    Image(
        painter = painterResource(
            id = when (index) {
                0 -> R.drawable.background_1
                1 -> R.drawable.background_2
                2 -> R.drawable.background_3
                else -> R.drawable.background_1
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = modifier,
    )
}