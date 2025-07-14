package com.saegil.ai_conversation.aiconversation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.ai_conversation.R
import com.saegil.ai_conversation.SaegilCharacter
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.body2
import com.saegil.designsystem.theme.h1
import com.saegil.designsystem.theme.h3

@Composable
fun AiConversationScreen(
    character: SaegilCharacter?,
    modifier: Modifier = Modifier,
    viewModel: AiConversationViewModel = hiltViewModel(),
    navigateToAiConversationList: () -> Unit = {},
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    InternalAiConversationScreen(
        state = state,
        modifier = modifier,
        onStopButtonClick = viewModel::stopChatSession,
        navigateToAiConversationList = navigateToAiConversationList,
    )

}

@Composable
internal fun InternalAiConversationScreen(
    state: AiConversationState,
    modifier: Modifier,
    onStopButtonClick: () -> Unit = {},
    navigateToAiConversationList: () -> Unit = {},
) {

    val context = LocalContext.current

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED
    ) {

        ActivityCompat.requestPermissions(
            context as Activity, // 여기가 중요!
            arrayOf(Manifest.permission.RECORD_AUDIO),
            1001
        )
    }
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE7F2FF), // 시작 색상 (연한 하늘색)
                        Color(0xFFFFFFFF)  // 끝 색상 (흰색)
                    )
                )
            ),
        color = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("전화 거는 중...",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(18.dp))
            Box(
                Modifier.border(
                    1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp) // ← 코너를 4dp만큼 둥글게
                )
            ) {
                Text(
                    "동갑내기 친구와 \"반말로\" 편안하게 대화해보세요!",
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.body2
                )

            }
            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painterResource(R.drawable.img_saerom),
                modifier = Modifier.size(200.dp),
                contentDescription = "새롬"
            )

            Text(
                "새롬",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(top = 18.dp)
            )
            Spacer(modifier = Modifier.height(120.dp))

            Image(
                painterResource(R.drawable.img_btn_end_call),
                modifier = Modifier
                    .size(96.dp)
                    .clickable {
                        onStopButtonClick()
                        navigateToAiConversationList()
                    },
                contentDescription = "전화 종료"
            )
        }


    }
}

@Composable
@Preview(name = "AiConversation")
private fun AiConversationScreenPreview() {
    SaegilAndroidTheme {
        InternalAiConversationScreen(
            state = AiConversationState(),
            modifier = Modifier
        )
    }
}

