package com.saegil.loglist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LogListScreen(
    modifier: Modifier = Modifier,
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