package com.saegil.learning.learning

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class LearningCoordinator(
    val viewModel: LearningViewModel
) {
    val screenStateFlow = viewModel.stateFlow


}

@Composable
fun rememberLearningCoordinator(
    viewModel: LearningViewModel = hiltViewModel()
): LearningCoordinator {
    return remember(viewModel) {
        LearningCoordinator(
            viewModel = viewModel
        )
    }
}