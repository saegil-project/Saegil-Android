package com.saegil.log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.log.component.MessageBubble

@Composable
fun LogScreen(
    modifier: Modifier = Modifier,
    viewModel: LogViewModel = hiltViewModel(),
    navigateToLogList: () -> Unit = {},
) {
    LogScreen(
        modifier = modifier,
    )
}

@Composable
internal fun LogScreen(
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MessageBubble(
                isUser = false,
                message = "안녕하세요. 어떤 거 찾으세요?"
            )
            MessageBubble(
                isUser = true,
                message = "아, 안녕하세요. 꼬부랑 국수는 어디에 있습니까?"
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun LogScreenPreview() {
    SaegilAndroidTheme {
        LogScreen()
    }
}