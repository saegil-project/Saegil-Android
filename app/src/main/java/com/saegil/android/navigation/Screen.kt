package com.saegil.android.navigation

import androidx.annotation.DrawableRes
import com.saegil.android.R

sealed class Screen(val route: String, @DrawableRes val icon: Int?, val label: String) {
    object AiConversation : Screen("ai_conversation", R.drawable.ic_pencil, "AI 대화")

    object Learning : Screen("learning", R.drawable.ic_pencil, "학습")
    object Announcement : Screen("announcement", R.drawable.ic_announcement, "공지사항")
    object Map : Screen("map", R.drawable.ic_location, "지도")
    object MyPage : Screen("mypage", R.drawable.ic_user_circle, "마이페이지")
    object News : Screen("news", R.drawable.ic_news, "뉴스")
    data object Splash : Screen("splash", null, "스플래시")
    data object Onboarding : Screen("onboarding", null, "온보딩")
    data object LogList : Screen("loglist", null, "로그리스트")
    data object Log : Screen("log", null, "로그")
    data object Quiz : Screen("quiz", null, "퀴즈")

    companion object {
        val items = listOf(AiConversation, Announcement, News, MyPage)
    }
}