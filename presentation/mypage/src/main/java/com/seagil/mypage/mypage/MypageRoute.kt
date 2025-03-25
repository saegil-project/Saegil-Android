package com.seagil.mypage.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


//@Composable
//fun MypageRoute(
//    coordinator: MypageCoordinator = rememberMypageCoordinator()
//) {
//    // State observing and declarations
//    val uiState by coordinator.screenStateFlow.collectAsState(MypageState())
//
//    // UI Actions
//    val actions = rememberMypageActions(coordinator)
//
//    // UI Rendering
//    MypageScreen(uiState, actions)
//}


@Composable
fun rememberMypageActions(coordinator: MypageCoordinator): MypageActions {
    return remember(coordinator) {
        MypageActions(

        )
    }
}
