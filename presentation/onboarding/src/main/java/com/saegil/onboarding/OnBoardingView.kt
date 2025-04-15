package com.saegil.onboarding

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.saegil.onboarding.component.KaKaoButton

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    OnBoardingScreen(
        context = context,
        onKaKaoButtonClick = viewModel::handleKaKaoLogin
    )
}

@Composable
internal fun OnBoardingScreen(
    context: Context,
    onKaKaoButtonClick: (Context) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text("온보딩 텍스트1")
        KaKaoButton(
            onClick = { onKaKaoButtonClick(context) },
        )
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    OnBoardingView()
}
