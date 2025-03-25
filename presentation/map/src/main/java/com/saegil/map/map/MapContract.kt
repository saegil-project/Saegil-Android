package com.saegil.map.map


/**
 * UI State that represents MapScreen
 **/
class MapState

/**
 * Map Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class MapActions(
    val onClick: () -> Unit = {}
)


