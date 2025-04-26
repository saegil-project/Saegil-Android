package com.saegil.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.saegil.android.navigation.NavGraph
import com.saegil.android.navigation.ui.BottomNavigationBar
import com.saegil.designsystem.component.SaegilTopBar
import com.saegil.designsystem.theme.SaegilAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
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

    Scaffold(
        topBar = {
            SaegilTopBar(
                showLogo = true,
                showBackButton = false
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
    ) { paddingValues ->
        NavGraph(navController = navController, modifier = Modifier.padding(paddingValues))
    }
}
