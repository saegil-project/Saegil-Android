package com.saegil.learning.learning

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LearningScreen(
    state: LearingState,
//    actions: LearingActions
) {
    // TODO UI Rendering
}

@Composable
@Preview(name = "Learing")
private fun LearingScreenPreview() {
    LearningScreen(
        state = LearingState(),
//        actions = LearingActions()
    )
}

