package com.saegil.onboarding.component

sealed class OnboardingPage(
    val title: String,
    val description: String,
    val showButton: Boolean = false
) {
    data object OnboardingFirst :
        OnboardingPage("언제 어디서나 대화 연습", "AI 전화 회화를 통해 일상에서 자주\n일어나는 상황 속 대화를 연습할 수 있어요.")

    data object OnboardingSecond :
        OnboardingPage("공지사항을 놓치지 않게", "푸시알림을 통해 정착에 도움이 되는\n공지사항을 제 때 확인할 수 있어요.")

    data object OnboardingThird : OnboardingPage(
        "관심사에 맞는 뉴스로 학습",
        "관심사에 따라 하루 5개의 뉴스, OX 퀴즈로\n독해력과 사회 이해도를 향상시킬 수 있어요.",
        showButton = true
    )

    companion object {
        val pages = listOf(
            OnboardingFirst,
            OnboardingSecond,
            OnboardingThird
        )
    }
}

