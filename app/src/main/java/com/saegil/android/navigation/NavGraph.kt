package com.saegil.android.navigation

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.saegil.learning.learning.LearningScreen
import com.saegil.learning.learning_list.LearningListScreen
import com.saegil.map.map.MapScreen
import com.saegil.mypage.mypage.MypageScreen
import com.saegil.onboarding.OnboardingScreen
import com.saegil.splash.SplashScreen
import com.saegil.notice.notice.NoticeScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {

    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Learning.route) {
            LearningListScreen(
                modifier = modifier,
                onScenarioClick = { scenarioId, scenarioName ->
                    navController.navigate("${Screen.Learning.route}/$scenarioId/$scenarioName")
                }
            )
        }
        composable(
            route = "${Screen.Learning.route}/{scenarioId}/{scenarioName}",
            arguments = listOf(
                navArgument("scenarioId") { type = NavType.LongType },
                navArgument("scenarioName") { type = NavType.StringType },

            )
        ) { backStackEntry ->
            val scenarioId = backStackEntry.arguments?.getLong("scenarioId") ?: 0
            val scenarioName = backStackEntry.arguments?.getString("scenarioName") ?: ""
            LearningScreen(
                modifier = modifier,
                scenarioId = scenarioId,
                scenarioName = scenarioName
            )
        }
        composable(Screen.Announcement.route) {
            NoticeScreen(
                modifier = modifier,
                navigateToWebView = { url ->
                    CustomTabsIntent.Builder().build().also {
                        it.launchUrl(context, Uri.parse(url))
                    }
                }
            )
        }
        composable(Screen.Map.route) {
            MapScreen(
                modifier = modifier
            )
        }
        composable(Screen.MyPage.route) {
            MypageScreen(
                modifier = modifier,
                navigateToWebView = { url ->
                    CustomTabsIntent.Builder().build().also {
                        it.launchUrl(context, Uri.parse(url))
                    }
                },
                navigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                navigateToLogList = {}
            )
        }
        composable(Screen.LogList.route) {

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
