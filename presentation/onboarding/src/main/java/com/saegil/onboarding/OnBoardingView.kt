package com.saegil.onboarding

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saegil.designsystem.theme.body
import com.saegil.onboarding.component.KaKaoButton
import com.saegil.onboarding.OnBoardingState.*
import com.saegil.designsystem.theme.h1
import com.saegil.onboarding.component.BirdIcon
import com.saegil.onboarding.component.OnBoardingBackground
import com.saegil.onboarding.component.ProgressBar

@Composable
fun OnBoardingScreen(
    navigateToMain: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val loginState by viewModel.loginUiState.collectAsStateWithLifecycle()

    OnBoardingScreen(
        loginState = loginState,
        context = context,
        onKaKaoButtonClick = viewModel::kakaoLogin,
        navigateToMain = navigateToMain,
        modifier = modifier,
    )
}

@Composable
internal fun OnBoardingScreen(
    loginState: OnBoardingState,
    context: Context,
    onKaKaoButtonClick: (Context) -> Unit,
    navigateToMain: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (loginState) {
        Failure -> Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
        Success -> navigateToMain()
        else -> {}
    }
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_3), // 배경 이미지
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
        )

        // 그 위에 UI 올리기
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "한 눈에 보는 근처 복지시설",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "현재 있는 장소 근처에 있는 복지시설의\n위치와 기관 정보를 쉽게 찾아볼 수 있어요.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            KaKaoButton(
                onClick = { onKaKaoButtonClick(context) },
            )
        }
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        OnBoardingBackground(
            index = 3,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .align(Alignment.BottomCenter)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressBar(3)
            Spacer(modifier = Modifier.padding(10.dp))
            Column {
                Text(
                    text = "한 눈에 보는 근처 복지시설",
                    style = MaterialTheme.typography.h1,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(13.dp))
                Text(
                    text = "현재 있는 장소 근처에 있는 복지시설의\n위치와 기관 정보를 쉽게 찾아볼 수 있어요.",
                    style = MaterialTheme.typography.body,
                    textAlign = TextAlign.Center
                )
            }
            BirdIcon(3)
            Spacer(modifier = Modifier.weight(1f))
            KaKaoButton(
                onClick = { },
                modifier = Modifier.padding(bottom = 25.dp)
            )
        }
    }
}
