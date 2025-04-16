package com.saegil.onboarding

sealed interface OnBoardingState {

    data object Idle : OnBoardingState
    data object Loading : OnBoardingState
    data object Success : OnBoardingState
    data object Failure : OnBoardingState

}