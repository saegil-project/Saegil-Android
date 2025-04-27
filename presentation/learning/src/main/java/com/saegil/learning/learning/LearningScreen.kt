package com.saegil.learning.learning

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.h1
import com.saegil.designsystem.theme.h2
import com.saegil.designsystem.theme.h3
import com.saegil.learning.learning.components.CharacterEmotion

@Composable
fun LearningScreen(
    modifier: Modifier = Modifier,
    scenarioId: Long,
    scenarioName: String = "",
    viewModel: LearningViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showPermissionDialog by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.startRecording()
        } else {
            showPermissionDialog = true
        }
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("마이크 권한 필요") },
            text = { Text("녹음을 위해 마이크 권한이 필요합니다. 설정에서 권한을 허용해주세요.") },
            confirmButton = {
                Button(
                    onClick = { showPermissionDialog = false }
                ) {
                    Text("확인")
                }
            }
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "상황 $scenarioId",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(top = 20.dp, bottom = 14.dp)
            )
            Text(
                text = scenarioName,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colorScheme.primary
            )

            SaegilCharacter(
                modifier = Modifier.padding(top = 117.dp),
                characterEmotion = CharacterEmotion.NORMAL
            )

            Text(
                text = "content", //Todo 수정
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(top = 30.dp),
                textAlign = TextAlign.Center
            )

            when (state) {
                is LearningUiState.Error -> {
                    Text(
                        text = "error",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(top = 100.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is LearningUiState.isConverting -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 100.dp)
                    )
                }

                is LearningUiState.isUploading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 100.dp)
                    )
                }

                else -> {
                    RecordButton(
                        modifier = Modifier.padding(top = 100.dp),
                        isRecording = state == LearningUiState.isRecording,
                        onClick = {
                            if (state == LearningUiState.isRecording) {
                                viewModel.stopRecording()
                            } else {
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
                        }
                    )
                }
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
    modifier: Modifier,
    isRecording: Boolean,
    onClick: () -> Unit
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

@Composable
@Preview(name = "Learning")
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

