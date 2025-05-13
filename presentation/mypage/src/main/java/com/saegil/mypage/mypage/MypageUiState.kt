package com.saegil.mypage.mypage

sealed interface MypageUiState {

    data object Loading: MypageUiState

    data class Success(
        val userName: String
    ): MypageUiState
}