package com.saegil.android.navigation.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.saegil.android.navigation.Screen
import com.saegil.designsystem.theme.BackgroundGray
import com.saegil.designsystem.theme.MainYellow
import com.saegil.designsystem.theme.TextGray

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    Surface(
        shadowElevation = 8.dp,
        color = TextGray,
    ) {
        NavigationBar(
            tonalElevation = 0.dp,
            containerColor = BackgroundGray
        ) {
            Screen.items.forEach { screen ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.label,
                        )
                    },
                    label = {
                        Text(
                            text = screen.label,
                        )
                    },
                    selected = currentDestination == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MainYellow,
                        selectedTextColor = MainYellow,
                        indicatorColor = BackgroundGray
                    )
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewBottomNavigationBar() {
    val navController = rememberNavController()

    // 테마 적용을 위해 MaterialTheme으로 감싸기
    MaterialTheme {
        BottomNavigationBar(navController = navController)
    }
}