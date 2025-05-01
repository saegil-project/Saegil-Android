package com.saegil.loglist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LogListScreen(
    modifier: Modifier = Modifier,
    viewModel: LogListViewModel = hiltViewModel(),
    navigateToLog: (Int) -> Unit = {},
    navigateToMypage: () -> Unit = {},
) {

    LogListScreen()
}

@Composable
internal fun LogListScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn() {

    }
}