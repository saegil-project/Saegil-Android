package com.saegil.learning.learning

import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.DownloadAudioUseCase
import com.saegil.domain.usecase.UploadAudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.onFailure

@HiltViewModel
class LearningViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val uploadAudioUseCase: UploadAudioUseCase,
    private val downloadAudioUseCase: DownloadAudioUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LearningUiState>(LearningUiState.Idle)
    val uiState: StateFlow<LearningUiState> = _uiState.asStateFlow()

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var audioFile: File? = null

    private fun checkAndRequestPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun startRecording() {
        if (!checkAndRequestPermission()) {
            _uiState.value = LearningUiState.Error("마이크 권한이 필요합니다")
            return
        }

        runCatching {
            val fileName = "recording_${System.currentTimeMillis()}.m4a"
            audioFile = File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName)

            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile?.absolutePath)
                prepare()
                start()
            }
            _uiState.value = LearningUiState.isRecording
        }.onFailure {
            _uiState.value = LearningUiState.Error("녹음 시작 중 오류가 발생했습니다")
        }
    }

    fun stopRecording() {
        runCatching {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            _uiState.value = LearningUiState.Idle
            exchangeAudio()
        }.onFailure {
            _uiState.value = LearningUiState.Error("녹음 중지 중 오류가 발생했습니다")
        }
    }

    private fun exchangeAudio() {
        viewModelScope.launch {
            runCatching {
                audioFile?.let { file ->
                    _uiState.value = LearningUiState.isUploading

                    runCatching {
                        uploadAudioUseCase(file).let { dto ->
                            Log.d("경로","시작")
                            downloadAudio(dto.response)
                            Log.d("경로","끝")
                            _uiState.value = LearningUiState.Success(dto)
                        }
                    }.onFailure {
                        _uiState.value = LearningUiState.Error("파일 업로드 중 오류가 발생했습니다")
                    }
                }
            }.onFailure {
                _uiState.value = LearningUiState.Error("파일 변환 중 오류가 발생했습니다")
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        if (_uiState.value == LearningUiState.isRecording) {
            stopRecording()
        }
    }

    private suspend fun downloadAudio(text: String) {
        runCatching {
            downloadAudioUseCase(text)
                .catch {
                    _uiState.value = LearningUiState.Error("오디오 다운로드 실패")
                }
                .collect { file ->
                    playAudio(file)
                }
        }.onFailure {
            _uiState.value = LearningUiState.Error("오디오 처리 중 오류 발생")
        }
    }

    private fun playAudio(file: File) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(file.absolutePath)
            prepare()
            start()
        }
    }
}
