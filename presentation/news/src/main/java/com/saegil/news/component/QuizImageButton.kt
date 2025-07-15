package com.saegil.news.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.R
import com.saegil.designsystem.theme.SaegilAndroidTheme

@Composable
fun QuizImageButton(
    answer: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val imageRes = if (answer) R.drawable.ic_o else R.drawable.ic_x

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "OX",
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
@Preview
fun QuizImageButtonPreview() {
    SaegilAndroidTheme {
        QuizImageButton(answer = true)
    }
}
