package com.saegil.android.navigation

import androidx.annotation.DrawableRes
import com.saegil.android.R

sealed class Screen(val route: String, @DrawableRes val icon: Int, val label: String) {
    object Learning : Screen("learning", R.drawable.ic_launcher_foreground, "학습")
    object Announcement : Screen("announcement", R.drawable.ic_launcher_foreground, "공지사항")
    object Map : Screen("map", R.drawable.ic_launcher_foreground, "지도")
    object MyPage : Screen("mypage", R.drawable.ic_launcher_foreground, "마이페이지")

    companion object {
        val items = listOf(Learning, Announcement, Map, MyPage)
    }
}