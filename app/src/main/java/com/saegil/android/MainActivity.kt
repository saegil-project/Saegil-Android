package com.saegil.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.saegil.android.navigation.NavGraph
import com.saegil.android.navigation.Screen
import com.saegil.android.navigation.ui.BottomNavigationBar
import com.saegil.designsystem.component.SaegilTopBar
import com.saegil.designsystem.theme.SaegilAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SaegilAndroidTheme {
                MainScreen()
            }
        }
    }
}

@Composable
@Preview
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val noBars = currentRoute in setOf(
        Screen.Splash.route,
        Screen.Onboarding.route,
        Screen.LogList.route,
        Screen.Log.route,
    ) || listOf(
        Screen.Learning.route,
        Screen.Log.route,
        Screen.LogList.route,
    ).any { prefix -> currentRoute?.startsWith("$prefix/") == true }

    Scaffold(
        topBar = {
            if (!noBars) SaegilTopBar()
        },
        bottomBar = { if (!noBars) BottomNavigationBar(navController) },
    ) { paddingValues ->
        NavGraph(navController = navController, modifier = if(!noBars) Modifier.padding(paddingValues) else Modifier)
    }
}
