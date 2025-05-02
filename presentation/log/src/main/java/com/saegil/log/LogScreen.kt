package com.saegil.log

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LogScreen(
    modifier: Modifier = Modifier,
    viewModel: LogViewModel = hiltViewModel(),
    navigateToLogList: () -> Unit = {},
) {

}
