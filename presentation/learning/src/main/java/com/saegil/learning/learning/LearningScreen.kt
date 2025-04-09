package com.saegil.learning.learning

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun LearningScreen(
    modifier : Modifier = Modifier
//    state: LearningState,
//    actions: LearningActions
) {
    // TODO UI Rendering
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "학습 화면", fontSize = 24.sp)
    }
}

@Composable
@Preview(name = "Learning")
private fun LearningScreenPreview() {
    LearningScreen(
//        state = LearningState(),
//        actions = LearningActions()
    )
}

