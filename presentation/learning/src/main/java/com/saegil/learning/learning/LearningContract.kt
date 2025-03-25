package com.saegil.learning.learning


/**
 * UI State that represents LearningScreen
 **/
class LearningState

/**
 * Learning Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class LearningActions(
    val onClick: () -> Unit = {}
)


