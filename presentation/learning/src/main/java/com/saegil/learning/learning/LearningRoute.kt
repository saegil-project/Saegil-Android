package com.saegil.learning.learning

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


//@Composable
//fun LearningRoute(
//    coordinator: LearningCoordinator = rememberLearningCoordinator()
//) {
//    // State observing and declarations
//    val uiState by coordinator.screenStateFlow.collectAsState(LearningState())
//
//    // UI Actions
//    val actions = rememberLearningActions(coordinator)
//
//    // UI Rendering
//    LearningScreen(uiState, actions)
//}


@Composable
fun rememberLearningActions(coordinator: LearningCoordinator): LearningActions {
    return remember(coordinator) {
        LearningActions(

        )
    }
}
