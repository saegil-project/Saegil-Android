package com.saegil.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saegil.learning.learning_list.LearningListScreen
import com.saegil.map.map.MapScreen
import com.saegil.mypage.mypage.MypageScreen
import com.saegil.notice.notice.NoticeScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Screen.Learning.route) {
        composable(Screen.Learning.route) {
            LearningListScreen(
                modifier = modifier
            )
        }
        composable(Screen.Announcement.route) {
            NoticeScreen(
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
