package com.saegil.learning.learning

import com.saegil.domain.model.UploadAudio

sealed interface LearningUiState {

    data object Idle : LearningUiState

    data object isRecording : LearningUiState

    data object isConverting : LearningUiState

    data object isUploading : LearningUiState

    data class Success(val response: UploadAudio) : LearningUiState

    data class Error(val message: String) : LearningUiState

}