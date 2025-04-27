package com.saegil.learning.learning

import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saegil.domain.usecase.UploadAudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val uploadAudioUseCase: UploadAudioUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<LearningUiState>(LearningUiState.Idle)
    val uiState: StateFlow<LearningUiState> = _uiState.asStateFlow()

    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null

    fun checkAndRequestPermission(): Boolean {
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

        try {
            val fileName = "recording_${System.currentTimeMillis()}.mp3"
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
        } catch (e: IOException) {
            _uiState.value = LearningUiState.Error("녹음 시작 중 오류가 발생했습니다")
        }
    }

    fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            _uiState.value = LearningUiState.Idle
            convertAndUpload()
        } catch (e: Exception) {
            _uiState.value = LearningUiState.Error("녹음 중지 중 오류가 발생했습니다")
        }
    }

    private fun convertAndUpload() {
        viewModelScope.launch {
            try {
                _uiState.value = LearningUiState.isConverting
                audioFile?.let { file ->
                    _uiState.value = LearningUiState.isUploading

                    val requestFile = file.asRequestBody("audio/mp3".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                    try {
                        val response = uploadAudioUseCase(body)
                        _uiState.value = LearningUiState.Success(response)
                    } catch (e: Exception) {
                        _uiState.value = LearningUiState.Error("파일 업로드 중 오류가 발생했습니다")
                    }
                }
            } catch (e: Exception) {
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
}