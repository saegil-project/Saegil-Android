package com.saegil.learning.learning_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.learning.learning_list.components.ScenarioItem

@Composable
fun LearningListScreen(
    modifier: Modifier = Modifier,
    onScenarioClick: (Int) -> Unit = {},
    viewModel: LearningListViewModel = hiltViewModel(),
) {
    val learningListState by viewModel.learningListUiState.collectAsStateWithLifecycle()

    LearningListScreen(
        learningListState = learningListState,
        modifier = modifier,
        onScenarioClick = onScenarioClick
    )
}

@Composable
internal fun LearningListScreen(
    learningListState: LearningListUiState,
    modifier: Modifier = Modifier,
    onScenarioClick: (Int) -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            SaegilTitleText(
                "시뮬레이션 학습",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        when (learningListState) {
            is LearningListUiState.Loading -> {

            }

            is LearningListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(36.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(learningListState.organizationList) { item ->
                        ScenarioItem(
                            item = item,
                            onClick = { onScenarioClick(item.id.toInt()) }
                        )
                    }
                }
            }
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

