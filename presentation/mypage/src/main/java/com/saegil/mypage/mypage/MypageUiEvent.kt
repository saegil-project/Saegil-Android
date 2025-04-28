package com.saegil.mypage.mypage

sealed interface MypageUiEvent {
    data object SuccessLogout : MypageUiEvent
    data object FailureLogout : MypageUiEvent
    data object SuccessWithdrawal : MypageUiEvent
    data object FailureWithdrawal : MypageUiEvent
}