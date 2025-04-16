package com.saegil.onboarding

sealed interface OnboardingState {

    data object Idle : OnboardingState
    data object Loading : OnboardingState
    data object Success : OnboardingState
    data object Failure : OnboardingState

}