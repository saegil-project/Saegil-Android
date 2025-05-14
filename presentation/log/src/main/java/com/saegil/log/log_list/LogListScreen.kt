package com.saegil.log.log_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.designsystem.component.SaegilLoadingWheel
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.component.ScenarioItem
import com.saegil.domain.model.SimulationLog
import com.saegil.log.log_list.component.EmptyLogImage

@Composable
fun LogListScreen(
    modifier: Modifier = Modifier,
    viewModel: LogListViewModel = hiltViewModel(),
    navigateToLog: (Long) -> Unit = {},
    navigateToMypage: () -> Unit = {},
) {

    val logListState by viewModel.logListUiState.collectAsStateWithLifecycle()

    LogListScreen(
        logListState = logListState,
        navigateToLog = navigateToLog,
        modifier = modifier,
        navigateToMypage = navigateToMypage
    )
}

@Composable
internal fun LogListScreen(
    logListState: LogListUiState,
    modifier: Modifier = Modifier,
    navigateToLog: (Long) -> Unit = {},
    navigateToMypage: () -> Unit = {},
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize(),
        ) {
            SaegilTitleText(
                title = "학습 대화 내역",
                onBackClick = navigateToMypage,
            )
            when (logListState) {
                LogListUiState.Loading -> LoadingState()
                is LogListUiState.Success -> ScenarioLogList(
                    logListState = logListState,
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
    logListState: LogListUiState.Success,
    modifier: Modifier = Modifier,
    navigateToLog: (Long) -> Unit = {},
) {
    logListState.logList?.let {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
            contentPadding = PaddingValues(36.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            scenarioLogList(
                logList = logListState.logList,
                navigateToLog = navigateToLog,
            )
        }
    } ?: EmptyLogImage()
}

fun LazyListScope.scenarioLogList(
    logList: List<SimulationLog>,
    navigateToLog: (Long) -> Unit = {},
) {
    items(logList) { simulationLog ->
        ScenarioItem(
            name = simulationLog.scenarioName,
            iconImageUrl = simulationLog.scenarioIconImageUrl,
            onClick = { navigateToLog(simulationLog.id) },
            date = simulationLog.createdAt
        )
    }
}