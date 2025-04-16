package com.saegil.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saegil.notice.notice.NoticeScreen
import com.saegil.learning.learning.LearningScreen
import com.saegil.map.map.MapScreen
import com.saegil.mypage.mypage.MypageScreen
import com.saegil.onboarding.OnboardingScreen
import com.saegil.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Learning.route) {
            LearningScreen(
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
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                navigateToMain = {
                    navController.navigate(Screen.Learning.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Splash.route) {
            SplashScreen(
                navigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                navigateToMain = {
                    navController.navigate(Screen.Learning.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
            )
        }
    }
}
