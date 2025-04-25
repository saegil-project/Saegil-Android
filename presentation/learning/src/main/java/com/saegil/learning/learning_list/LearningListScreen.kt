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
import com.saegil.domain.model.Simulation
import com.saegil.learning.learning_list.components.SimulationItem

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
            contentPadding = PaddingValues(36.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(items) { item ->
                SimulationItem(item)
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

