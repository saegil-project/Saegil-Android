package com.saegil.onboarding.component

sealed class OnboardingPage(
    val title: String,
    val description: String,
    val showButton: Boolean = false
) {
    data object OnboardingFirst :
        OnboardingPage("언제 어디서나 대화 연습", "AI 시뮬레이션 대화를 통해 일상에서 자주\n일어나는 상황 속 대화를 연습할 수 있어요.")

    data object OnboardingSecond :
        OnboardingPage("공지사항을 한 곳에서 확인", "여기저기 흩어진 있던 북한이탈주민 대상\n공지사항들을 편하게 확인할 수 있어요.")

    data object OnboardingThird : OnboardingPage(
        "한 눈에 보는 근처 복지시설",
        "현재 있는 장소 근처에 있는 복지시설의\n위치와 기관 정보를 쉽게 찾아볼 수 있어요.",
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

