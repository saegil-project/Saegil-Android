package com.saegil.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saegil.onboarding.R

@Composable
fun BirdIcon(
    index: Int,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = when (index) {
                    0 -> 400.dp
                    1 -> 270.dp
                    2 -> 50.dp
                    else -> 10.dp
                }
            )
            .wrapContentHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_bird),
            contentDescription = null,
            modifier = Modifier
                .width(50.dp)
                .height(40.dp)
                .align(
                    when (index) {
                        0 -> Alignment.CenterStart
                        1 -> Alignment.Center
                        2 -> Alignment.CenterEnd
                        else -> Alignment.Center
                    }
                ),
            contentScale = ContentScale.FillBounds,
        )
    }
}