package com.saegil.log.log.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.caption

@Composable
fun MessageBubble(
    isUser: Boolean,
    modifier: Modifier = Modifier,
    message: String = "",
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = if (isUser) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary,
            modifier = modifier
                .padding(
                    start = if (isUser) 100.dp else 20.dp,
                    end = if (isUser) 20.dp else 100.dp,
                )
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun UserMessageBubblePreview() {
    SaegilAndroidTheme {
        MessageBubble(
            isUser = true,
            message = "라면은 어디에 있습니까?"
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun AiMessageBubblePreview() {
    SaegilAndroidTheme {
        MessageBubble(
            isUser = false,
            message = "방금의 대화에서 ‘꼬부랑 국수’는 북한에서 사용하는 단어이며, 한국어로 ‘라면’이라고 부릅니다. 꼬부랑 국수 대신 라면을 활용하여 다시 말씀해보세요."
        )
    }
}