package com.saegil.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.caption
import com.saegil.designsystem.theme.h3

@Composable
fun ScenarioItem(
    name: String,
    iconImageUrl: String,
    onClick: () -> Unit,
    date: String? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(top = 16.dp, start = 20.dp, end = 18.dp, bottom = 16.dp)
        ) {
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                date?.let {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(iconImageUrl)
                    .crossfade(true)
                    .build(),
//                        placeholder = painterResource(R.drawable), // 로딩 중 이미지
//                        error = painterResource(R.drawable.error), // 에러 시 이미지
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun ScenarioItemPreview() {
    SaegilAndroidTheme {
        ScenarioItem(
            name = "밥 먹을 때",
            iconImageUrl = "3",
            onClick = {},
            date = "1111111"
        )
    }
}