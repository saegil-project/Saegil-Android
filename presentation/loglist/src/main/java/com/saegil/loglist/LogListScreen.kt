package com.saegil.loglist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LogListScreen(
    modifier: Modifier = Modifier,
    viewModel: LogListViewModel = hiltViewModel(),
    navigateToLog: (Int) -> Unit = {},
    navigateToMypage: () -> Unit = {},
) {

    val logState by viewModel.logUiState.collectAsStateWithLifecycle()

    LogListScreen(
        logState = logState
    )
}

@Composable
internal fun LogListScreen(
    logState: LogListUiState,
    modifier: Modifier = Modifier,
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
        ) {

        }
    }
}