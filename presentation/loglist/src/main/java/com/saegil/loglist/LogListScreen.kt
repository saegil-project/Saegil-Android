package com.saegil.loglist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saegil.designsystem.component.SaegilLoadingWheel
import com.saegil.designsystem.component.ScenarioItem
import com.saegil.domain.model.Scenario
import com.saegil.loglist.component.EmptyLogImage

@Composable
fun LogListScreen(
    modifier: Modifier = Modifier,
    viewModel: LogListViewModel = hiltViewModel(),
    navigateToLog: (Long) -> Unit = {},
    navigateToMypage: () -> Unit = {},
) {

    val logState by viewModel.logUiState.collectAsStateWithLifecycle()
    val scenarioLogResource =
        (logState as? LogListUiState.Success)?.logList?.collectAsLazyPagingItems()

    LogListScreen(
        logState = logState,
        scenarioLogResource = scenarioLogResource,
        navigateToLog = navigateToLog,
        modifier = modifier
    )
}

@Composable
internal fun LogListScreen(
    logState: LogListUiState,
    scenarioLogResource: LazyPagingItems<Scenario>?,
    modifier: Modifier = Modifier,
    navigateToLog: (Long) -> Unit = {},
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
        ) {

            when (logState) {
                LogListUiState.Loading -> LoadingState()
                is LogListUiState.Success -> ScenarioLogList(
                    scenarioLogResource = scenarioLogResource,
                    navigateToLog = navigateToLog
                )
            }
        }
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier,
) {
    SaegilLoadingWheel(
        modifier = modifier
            .wrapContentSize()
    )
}

@Composable
private fun ScenarioLogList(
    scenarioLogResource: LazyPagingItems<Scenario>?,
    modifier: Modifier = Modifier,
    navigateToLog: (Long) -> Unit = {},
) {
    scenarioLogResource?.let {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(36.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            scenarioLogList(
                scenarioLogResource = scenarioLogResource,
                navigateToLog = navigateToLog,
            )
        }
    } ?: EmptyLogImage()
}

fun LazyListScope.scenarioLogList(
    scenarioLogResource: LazyPagingItems<Scenario>,
    navigateToLog: (Long) -> Unit = {},
) {
    items(scenarioLogResource.itemCount) { index ->
        val scenarioLog = scenarioLogResource[index]
        scenarioLog?.let { scenario ->
            ScenarioItem(
                name = scenario.name,
                iconImageUrl = scenario.iconImageUrl,
                onClick = { navigateToLog(scenario.id) }
            )
        }
    }
}