package com.saegil.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saegil.announcement.announcement.AnnouncementScreen
import com.saegil.learning.learning.LearningScreen
import com.saegil.map.map.MapScreen
import com.saegil.mypage.mypage.MypageScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Screen.Learning.route) {
        composable(Screen.Learning.route) {
            LearningScreen(
                modifier = modifier
            )
        }
        composable(Screen.Announcement.route) {
            AnnouncementScreen(
                modifier = modifier
            )
        }
        composable(Screen.Map.route) {
            MapScreen(
                modifier = modifier
            )
        }
        composable(Screen.MyPage.route) {
            MypageScreen(
                modifier = modifier
            )
        }
    }
}
