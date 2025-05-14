package com.saegil.onboarding

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.saegil.designsystem.theme.SaegilAndroidTheme
import com.saegil.designsystem.theme.body1
import com.saegil.onboarding.component.KakaoLoginButton
import com.saegil.onboarding.OnboardingState.*
import com.saegil.designsystem.theme.h1
import com.saegil.onboarding.component.OnboardingBackground
import com.saegil.onboarding.component.OnboardingPage
import com.saegil.onboarding.component.Indicator

@Composable
fun OnboardingScreen(
    navigateToMain: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val loginState by viewModel.loginUiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { OnboardingPage.pages.size })
    OnboardingScreen(
        pagerState = pagerState,
        loginState = loginState,
        context = context,
        loginWithKakaokakaoAccessToken = viewModel::loginWithKakaokakaoAccessToken,
        navigateToMain = navigateToMain,
        modifier = modifier,
    )
}

@Composable
internal fun OnboardingScreen(
    pagerState: PagerState,
    loginState: OnboardingState,
    context: Context,
    loginWithKakaokakaoAccessToken: (String) -> Unit,
    navigateToMain: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (loginState) {
        Failure -> Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
        Success -> navigateToMain()
        else -> {}
    }
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            OnboardingBackground(
                index = page,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Indicator(page)
                Spacer(modifier = Modifier.height(10.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = OnboardingPage.pages[page].title,
                        style = MaterialTheme.typography.h1,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = OnboardingPage.pages[page].description,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (OnboardingPage.pages[page].showButton) {
                    KakaoLoginButton(
                        onClick = {
                            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                                    error?.let {
                                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                            return@loginWithKakaoTalk
                                        }
                                    }
                                    token?.let {
                                        loginWithKakaokakaoAccessToken(token.accessToken)
                                    }
                                }
                            } else {
                                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->

                                    error?.let {
                                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                            return@loginWithKakaoAccount
                                        }
                                    }
                                    token?.let { loginWithKakaokakaoAccessToken(token.accessToken) }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 70.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(apiLevel = 33, device = Devices.NEXUS_5)
fun OnBoardingPreview() {
    SaegilAndroidTheme {
        OnboardingScreen(
            pagerState = rememberPagerState(pageCount = { OnboardingPage.pages.size }),
            loginState = Success,
            context = LocalContext.current,
            loginWithKakaokakaoAccessToken = {},
            navigateToMain = {}
        )
    }
}

@Composable
@Preview(apiLevel = 33, device = Devices.NEXUS_5)
fun OnBoarding2Preview() {
    SaegilAndroidTheme {
        OnboardingScreen(
            pagerState = rememberPagerState(initialPage = 1, pageCount = { OnboardingPage.pages.size }),
            loginState = Success,
            context = LocalContext.current,
            loginWithKakaokakaoAccessToken = {},
            navigateToMain = {}
        )
    }
}

@Composable
@Preview(apiLevel = 33, device = Devices.NEXUS_5)
fun OnBoarding3Preview() {
    SaegilAndroidTheme {
        OnboardingScreen(
            pagerState = rememberPagerState(initialPage = 2, pageCount = { OnboardingPage.pages.size }),
            loginState = Success,
            context = LocalContext.current,
            loginWithKakaokakaoAccessToken = {},
            navigateToMain = {}
        )
    }
}