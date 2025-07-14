package com.saegil.ai_conversation.aiconversation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.saegil.designsystem.theme.h1
import kotlin.reflect.KSuspendFunction0

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
//        onRequestToken = viewModel::onRequestToken,
//        startRealtimeChat = viewModel::startChatSession,
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
//    onRequestToken:
    startRealtimeChat: (String) -> Unit = {},
) {

//    LaunchedEffect(Unit) {
//        val token = viewModel.onRequestToken()
//        Log.d("UI", token)
//    }

//    val token: String = onRequestToken().toString()
//    startRealtimeChat(token.toString())
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
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    )
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("전화 거는 중...")
            Spacer(modifier = Modifier.height(10.dp))
            Text("동갑내기 친구와 \"반말로\" 편안하게 대화해보세요!")
            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painterResource(R.drawable.img_saerom),
                modifier = Modifier.size(200.dp),
                contentDescription = "새롬"
            )

            Text(
                "새롬",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(top = 10.dp)
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

