package com.seagil.announcement.announcement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


//@Composable
//fun AnnouncementRoute(
//    coordinator: AnnouncementCoordinator = rememberAnnouncementCoordinator()
//) {
//    // State observing and declarations
//    val uiState by coordinator.screenStateFlow.collectAsState(AnnouncementState())
//
//    // UI Actions
//    val actions = rememberAnnouncementActions(coordinator)
//
//    // UI Rendering
//    AnnouncementScreen(uiState, actions)
//}


@Composable
fun rememberAnnouncementActions(coordinator: AnnouncementCoordinator): AnnouncementActions {
    return remember(coordinator) {
        AnnouncementActions(

        )
    }
}
