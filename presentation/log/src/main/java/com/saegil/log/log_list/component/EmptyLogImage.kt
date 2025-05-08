package com.saegil.log.log_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.R
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.body1
import com.saegil.designsystem.theme.h2

@Composable
fun EmptyLogImage(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .offset(y = (-70).dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_wonder),
            contentDescription = "Empty Log List",
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "학습 대화 내역이 없습니다.",
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "지금 바로 학습 탭에서\n시뮬레이션 학습을 시작해보세요!",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun EmptyLogImagePreview() {
    SaegilAndroidTheme {
        EmptyLogImage()
    }
}