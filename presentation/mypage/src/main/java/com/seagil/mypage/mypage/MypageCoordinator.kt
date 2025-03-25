package com.seagil.mypage.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class MypageCoordinator(
    val viewModel: MypageViewModel
) {
    val screenStateFlow = viewModel.stateFlow


}

@Composable
fun rememberMypageCoordinator(
    viewModel: MypageViewModel = hiltViewModel()
): MypageCoordinator {
    return remember(viewModel) {
        MypageCoordinator(
            viewModel = viewModel
        )
    }
}