package com.saegil.learning.learning_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.domain.model.Scenario
import com.saegil.learning.learning_list.components.ScenarioItem

@Composable
fun LearningListScreen(
    modifier: Modifier = Modifier,
    onScenarioClick: (Int) -> Unit = {}
) {
    val items = listOf(
        Scenario(
            id = 1,
            name = "이름",
            iconImageUrl = "https://avatars.githubusercontent.com/u/171667914?s=48&v=4"
        ),
        Scenario(
            id = 2,
            name = "이름2",
            iconImageUrl = "https://avatars.githubusercontent.com/u/171667914?s=48&v=4"
        ),
    )
    LearningListScreen(
        items = items,
        modifier = modifier,
        onScenarioClick = onScenarioClick
    )
}

@Composable
internal fun LearningListScreen(
    items: List<Scenario>,
    modifier: Modifier = Modifier,
    onScenarioClick: (Int) -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(36.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                ScenarioItem(
                    item = item,
                    onClick = { onScenarioClick(item.id.toInt()) }
                )
            }
        }
    }
}

@Composable
@Preview(name = "Learning")
private fun LearningScreenPreview() {
    SaegilAndroidTheme {
        Surface {
            LearningListScreen()
        }
    }
}

