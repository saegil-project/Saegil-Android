package com.saegil.loglist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import com.saegil.domain.model.Scenario

@Composable
fun LogListScreen(
    modifier: Modifier = Modifier,
    viewModel: LogListViewModel = hiltViewModel(),
    navigateToLog: (Int) -> Unit = {},
    navigateToMypage: () -> Unit = {},
) {

    val logState by viewModel.logUiState.collectAsStateWithLifecycle()
    val scenarioLogResource = (logState as? LogListUiState.Success)?.logList?.collectAsLazyPagingItems()

    LogListScreen(
        logState = logState,
        scenarioLogResource = scenarioLogResource,
        modifier = modifier
    )
}

@Composable
internal fun LogListScreen(
    logState: LogListUiState,
    scenarioLogResource: LazyPagingItems<Scenario>?,
    modifier: Modifier = Modifier,
    navigateToLog: (Int) -> Unit = {},
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
                    sceanrioLogResource = scenarioLogResource,
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
    sceanrioLogResource: LazyPagingItems<Scenario>?,
    modifier: Modifier = Modifier,
    navigateToLog: (Int) -> Unit = {},
) {

}