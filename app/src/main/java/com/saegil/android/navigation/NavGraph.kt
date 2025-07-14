package com.saegil.android.navigation

import android.util.Log
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
import com.saegil.log.log.LogScreen
import com.saegil.log.log_list.LogListScreen
import com.saegil.mypage.mypage.MypageScreen
import com.saegil.notice.notice.NoticeScreen
import com.saegil.onboarding.OnboardingScreen
import com.saegil.splash.SplashScreen
import androidx.core.net.toUri
import com.saegil.ai_conversation.SaegilCharacter
import com.saegil.ai_conversation.aiconversation.AiConversationScreen
import com.saegil.ai_conversation.aiconversationlist.AiConversationListScreen
import com.saegil.learning.learning.components.CharacterEmotion
import com.saegil.news.news.NewsScreen
import com.saegil.news.newsquiz.NewsQuizScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {

    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.AiConversation.route) {
            AiConversationListScreen(
                modifier = modifier,
                onCharacterClick = { character ->
                    navController.navigate("${Screen.AiConversation.route}/$character")
                    Log.d("character", character)
                }
            )
        }
        composable(
            route = "${Screen.AiConversation.route}/{characterString}",
            arguments = listOf(navArgument("characterString") { type = NavType.StringType })
        ) { backStackEntry ->
            val characterString = backStackEntry.arguments?.getString("characterString")
            val character = runCatching { SaegilCharacter.valueOf(characterString ?: "") }.getOrNull()

            AiConversationScreen(character = character)
        }

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
                scenarioId = scenarioId,
                scenarioName = scenarioName,
                navigateToLearningList = { navController.popBackStack() }
            )
        }
        composable(Screen.Announcement.route) {
            NoticeScreen(
                modifier = modifier,
                navigateToWebView = { url ->
                    CustomTabsIntent.Builder().build().also {
                        it.launchUrl(context, url.toUri())
                    }
                }
            )
        }
        composable(Screen.News.route) {
            NewsScreen(
                modifier = modifier,
                navigateToQuiz = { id ->
                    navController.navigate("${Screen.Quiz.route}/$id")
                }
            )
        }
        composable(
            route = "${Screen.Quiz.route}/{quizId}",
            arguments = listOf(navArgument("quizId") { type = NavType.LongType })
        ) {
            NewsQuizScreen(
                modifier = modifier,
            )
        }
        composable(Screen.MyPage.route) {
            MypageScreen(
                modifier = modifier,
                navigateToWebView = { url ->
                    CustomTabsIntent.Builder().build().also {
                        it.launchUrl(context, url.toUri())
                    }
                },
                navigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                navigateToLogList = {
                    navController.navigate(Screen.LogList.route)
                }
            )
        }
        composable(Screen.LogList.route) {
            LogListScreen(
                navigateToLog = { simulationId ->
                    navController.navigate("${Screen.Log.route}/$simulationId")
                },
                navigateToMypage = { navController.popBackStack() }
            )
        }
        composable(
            route = "${Screen.Log.route}/{simulationId}",
            arguments = listOf(navArgument("simulationId") { type = NavType.LongType })
        ) {
            LogScreen(
                modifier = modifier,
                navigateToLogList = { navController.popBackStack() }
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
