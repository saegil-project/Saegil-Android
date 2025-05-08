package com.saegil.log.log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.designsystem.component.SaegilLoadingWheel
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.domain.model.SimulationLogDetail
import com.saegil.domain.model.SimulationMessage
import com.saegil.log.log.component.MessageBubble

@Composable
fun LogScreen(
    modifier: Modifier = Modifier,
    viewModel: LogViewModel = hiltViewModel(),
    navigateToLogList: () -> Unit = {},
) {

    val logState by viewModel.logUiState.collectAsStateWithLifecycle()

    LogScreen(
        logState = logState,
        modifier = modifier,
        navigateToLogList = navigateToLogList,
    )
}

@Composable
internal fun LogScreen(
    logState: LogUiState,
    modifier: Modifier = Modifier,
    navigateToLogList: () -> Unit = {},
) {
    Column {
        SaegilTitleText(
            title = when (logState) {
                LogUiState.Loading -> "로딩중"
                is LogUiState.Success -> logState.detail.scenarioName
            },
            onBackClick = navigateToLogList,
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            when (logState) {
                LogUiState.Loading -> LoadingState()
                is LogUiState.Success -> MessageLogList(simulationLogDetail = logState.detail)
            }
        }
    }
}

@Composable
fun MessageLogList(
    simulationLogDetail: SimulationLogDetail,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(simulationLogDetail.messages) {
            MessageBubble(
                isUser = it.isFromUser,
                message = it.contents
            )
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

@Preview(apiLevel = 33)
@Composable
fun LogScreenPreview() {
    SaegilAndroidTheme {
        LogScreen(
            logState = LogUiState.Success(
                detail = SimulationLogDetail(
                    scenarioName = "11",
                    messages = listOf(
                        SimulationMessage(
                            id = 1,
                            isFromUser = true,
                            contents = "반갑습니다.",
                            createdAt = "111111"
                        ),
                        SimulationMessage(
                            id = 1,
                            isFromUser = false,
                            contents = "반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. ",
                            createdAt = "111111"
                        ),
                        SimulationMessage(
                            id = 1,
                            isFromUser = true,
                            contents = "반갑습니다.반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. ",
                            createdAt = "111111"
                        ), SimulationMessage(
                            id = 1,
                            isFromUser = false,
                            contents = "반갑습니다.반갑습니다. 반갑습니다. 반갑습니다. 반갑습니다. ",
                            createdAt = "111111"
                        )
                    )
                )
            )
        )
    }
}