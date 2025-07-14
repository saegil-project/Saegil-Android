package com.saegil.learning.learning

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.saegil.designsystem.R
import com.saegil.designsystem.component.SaegilDialog
import com.saegil.designsystem.component.SaegilTitleText
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.h1
import com.saegil.designsystem.theme.h2
import com.saegil.designsystem.theme.h3
import com.saegil.learning.learning.components.CharacterEmotion
import kotlinx.coroutines.delay
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.header
import io.ktor.http.HttpMethod
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
@Composable
fun LearningScreen(
    modifier: Modifier = Modifier,
    navigateToLearningList: () -> Unit = {},
    scenarioId: Long,
    scenarioName: String = "",
    viewModel: LearningViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }
    var currentEmotion by remember { mutableStateOf(CharacterEmotion.SMILE) }
    var displayText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.startRecording()
        } else {
            showPermissionDialog = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setScenarioId(scenarioId)
    }

    LaunchedEffect(state) {
        when (state) {
            is LearningUiState.Uploading -> {
                while (true) {
                    currentEmotion = CharacterEmotion.NORMAL
                    delay(300)
                    currentEmotion = CharacterEmotion.SAYING
                    delay(300)
                }
            }

            is LearningUiState.Recording -> {
                currentEmotion = CharacterEmotion.WONDER
            }

            is LearningUiState.Idle -> {
                currentEmotion = CharacterEmotion.SMILE
                displayText = "상황을 설명하고\n대화 연습을 시작해보세요"
            }

            is LearningUiState.Success -> {
                currentEmotion = CharacterEmotion.NORMAL
                displayText = (state as LearningUiState.Success).response.response
            }

            is LearningUiState.Error -> {
                currentEmotion = CharacterEmotion.NORMAL
                displayText = (state as LearningUiState.Error).message
            }
        }
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("마이크 권한 필요") },
            text = { Text("녹음을 위해 마이크 권한이 필요합니다. 설정에서 권한을 허용해주세요.") },
            confirmButton = {
                Button(
                    onClick = {
                        showPermissionDialog = false
                    }
                ) {
                    Text("확인")
                }
            }
        )
    }

    BackHandler {
        showExitDialog = true
    }

    // 다이얼로그 표시
    if (showExitDialog) {
        SaegilDialog(
            onDismissRequest = { showExitDialog = false },
            title = "학습 대화 종료",
            description = "지금 학습 대화를 종료하시겠습니까?",
            positiveButtonText = "종료하기",
            onPositiveButtonClicked = {
                showExitDialog = false
                viewModel.finishLearning()
                navigateToLearningList()
            },
            negativeButtonText = "돌아가기",
            onNegativeButtonClicked = { showExitDialog = false },
        )
    }


    Box(
        modifier = Modifier
            .padding(WindowInsets.systemBars.asPaddingValues())
            .fillMaxSize(),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SaegilTitleText(
                title = "",
                onBackClick = {
                    showExitDialog = true
                },
            )
            Text(
                text = "상황 $scenarioId",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(bottom = 14.dp)
            )
            Text(
                text = scenarioName,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colorScheme.primary
            )

            SaegilCharacter(
                modifier = Modifier.padding(top = 117.dp),
                characterEmotion = currentEmotion
            )

            when (state) {

                is LearningUiState.Uploading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 100.dp)
                    )
                }

                is LearningUiState.Error, is LearningUiState.Success, is LearningUiState.Idle -> {
                    Text(
                        text = displayText,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier
                            .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }

                LearningUiState.Recording -> {
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is LearningUiState.Success, is LearningUiState.Idle -> {
                    RecordButton(
                        isRecording = false,
                        onClick = {
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.RECORD_AUDIO
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                viewModel.startRecording()
                            } else {
                                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            }
                        }
                    )
                }

                is LearningUiState.Recording -> {
                    RecordButton(
                        isRecording = true,
                        onClick = {
                            viewModel.stopRecording()
                        }
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
fun SaegilCharacter(
    modifier: Modifier,
    characterEmotion: CharacterEmotion
) {
    Image(
        modifier = modifier.size(134.dp),
        painter = painterResource(id = characterEmotion.img),
        contentDescription = "새길 캐릭터 이미지"
    )
}

@Composable
fun RecordButton(
    isRecording: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(126.dp)
            .clickable(onClick = onClick),
        painter = painterResource(
            id = if (isRecording) {
                R.drawable.img_button_record_stop
            } else {
                R.drawable.img_button_record_start
            }
        ),
        contentDescription = if (isRecording) "녹음 중지" else "녹음 시작"
    )
}



@Serializable
data class StartMessage(
    val type: String = "start",
    val model: String = "gpt-4o",
    val messages: List<Message>
)

@Serializable
data class Message(
    val role: String,
    val content: String
)

fun startOpenAIWebSocket(apiKey: String) {
    val client = HttpClient(CIO) {
        install(WebSockets)
    }

    CoroutineScope(Dispatchers.IO).launch {
        client.webSocket(
            method = HttpMethod.Get,
            host = "api.openai.com",
            path = "/v1/realtime",
            request = {
                header("Authorization", "Bearer $apiKey")
                header("Content-Type", "application/json")
            }
        ) {
            println("✅ WebSocket 연결 성공")

            // JSON 메시지 생성
            val message = StartMessage(
                messages = listOf(
                    Message("user", "Hello, how are you?")
                )
            )
            val json = Json.encodeToString(message)
            send(Frame.Text(json))

            // 수신 루프
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> println("📥 수신: ${frame.readText()}")
                    is Frame.Close -> println("❌ 연결 종료됨")
                    else -> {}
                }
            }
        }
    }
}


@Composable
@Preview(name = "Learning", apiLevel = 33)
private fun LearningScreenPreview() {
    SaegilAndroidTheme {
        Surface {
            LearningScreen(
                scenarioId = 1,
                scenarioName = "새로운 친구를 사귈 때"
            )
        }
    }
}

