package com.saegil.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.splash.LoginState.*

@Composable
fun SplashScreen(
    navigateToOnboarding: () -> Unit,
    navigateToMain : () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {

    val loginState by viewModel.loginUiState.collectAsStateWithLifecycle()

    SplashScreen(
        loginState = loginState,
        navigateToMain = navigateToMain,
        navigateToOnboarding = navigateToOnboarding,
    )
}

@Composable
internal fun SplashScreen(
    loginState: LoginState,
    navigateToMain: () -> Unit,
    navigateToOnboarding: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when(loginState) {
        Loading -> {
            Box(modifier = modifier
                .fillMaxSize()
                .padding(bottom = 90.dp),
                contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(R.drawable.ic_saegil_logo),
                    contentDescription = "앱 로고",
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                )
            }
        }
        LoggedIn -> {
            navigateToMain()
        }
        NotLoggedIn -> {
            navigateToOnboarding()
        }
    }
}

@Preview
@Composable
fun SplashPreview() {
    SplashScreen(
        Loading,{},{}
    )
}
