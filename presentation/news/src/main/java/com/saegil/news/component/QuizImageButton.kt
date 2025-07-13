package com.saegil.news.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = if (answer) "O" else "X",
            modifier = Modifier.size(48.dp)
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
