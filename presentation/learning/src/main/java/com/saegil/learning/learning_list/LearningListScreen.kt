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
import com.saegil.designsystem.component.ScenarioItem
import com.saegil.designsystem.theme.SaegilAndroidTheme

@Composable
fun LearningListScreen(
    modifier: Modifier = Modifier,
    onScenarioClick: (Long, String) -> Unit = { id, name -> },
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
    onScenarioClick: (Long, String) -> Unit = { id, name -> },
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            SaegilTitleText(
                "AI 대화 학습",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        when (learningListState) {
            is LearningListUiState.Loading -> {
                // TODO: Show loading state
            }
            is LearningListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(36.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(learningListState.organizationList) { item ->
                        ScenarioItem(
                            name = item.name,
                            iconImageUrl = item.iconImageUrl,
                            onClick = { onScenarioClick(item.id, item.name) }
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

