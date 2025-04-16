package com.saegil.onboarding.component

import android.widget.ProgressBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saegil.onboarding.R

@Composable
fun ProgressBar(
    index: Int,
) {
    Row(
        modifier = Modifier
            .padding(top = 80.dp)
            .padding(50.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
    ) {
        if(index==0) InProgressIcon() else ProgressIcon()
        if(index==1) InProgressIcon() else ProgressIcon()
        if(index==2) InProgressIcon() else ProgressIcon()
    }
}

@Composable
fun ProgressIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_progress_stage),
        contentDescription = null,
        modifier = Modifier
            .size(8.dp),
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
fun InProgressIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_in_progress),
        contentDescription = null,
        modifier = Modifier
            .width(30.dp)
            .height(8.dp),
        contentScale = ContentScale.FillBounds,
    )
}