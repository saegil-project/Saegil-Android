package com.saegil.learning.learning_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.saegil.designsystem.theme.h3
import com.saegil.domain.model.Simulation

@Composable
fun LearningListScreen(
    modifier: Modifier = Modifier
//    state: LearningState,
//    actions: LearningActions
) {
    val items = listOf(
        Simulation(
            id = 1,
            name = "이름",
            iconImageUrl = "https://avatars.githubusercontent.com/u/171667914?s=48&v=4"

        ),
        Simulation(
            id = 2,
            name = "이름2",
            iconImageUrl = "https://avatars.githubusercontent.com/u/171667914?s=48&v=4"
        ),
    )
    LearningListScreen(
        items = items,
        modifier = modifier
    )
}


@Composable
internal fun LearningListScreen(
    items: List<Simulation>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background

    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(items) { item ->
                SimulationItem(item) // 단일 항목으로 전달
            }
        }
    }

}


@Composable
fun SimulationItem(item: Simulation) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(72.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,

            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            // 텍스트 표시
            Text(
                text = item.name,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // 이미지 표시
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.iconImageUrl)
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

@Composable
@Preview(name = "Learning")
private fun LearningScreenPreview() {
//    LearningScreen(
////        state = LearningState(),
////        actions = LearningActions()
//    )

    SaegilAndroidTheme {
        Surface {
            LearningListScreen()
        }
    }
}

