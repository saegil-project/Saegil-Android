package com.saegil.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saegil.learning.learning.LearningScreen
import com.saegil.map.map.MapScreen
import com.seagil.announcement.announcement.AnnouncementScreen
import com.seagil.mypage.mypage.MypageScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Screen.Learning.route) {
        composable(Screen.Learning.route) {
            LearningScreen()
        }
        composable(Screen.Announcement.route) {
            AnnouncementScreen()
        }
        composable(Screen.Map.route) {
            MapScreen()
        }
        composable(Screen.MyPage.route) {
            MypageScreen()
        }
    }
}
