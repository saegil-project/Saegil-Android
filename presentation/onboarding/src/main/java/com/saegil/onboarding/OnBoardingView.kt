package com.saegil.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OnBoardingView(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {

}

@Composable
internal fun OnBoardingView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text("온보딩 텍스트1")

    }
}