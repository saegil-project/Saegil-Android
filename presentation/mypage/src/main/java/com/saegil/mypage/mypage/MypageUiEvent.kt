package com.saegil.mypage.mypage

sealed interface MypageUiEvent {
    data object SuccessLogout : MypageUiEvent
    data object FailureLogout : MypageUiEvent
}