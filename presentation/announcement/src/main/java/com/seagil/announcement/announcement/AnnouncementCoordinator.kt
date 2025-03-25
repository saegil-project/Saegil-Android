package com.seagil.announcement.announcement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class AnnouncementCoordinator(
    val viewModel: AnnouncementViewModel
) {
    val screenStateFlow = viewModel.stateFlow


}

@Composable
fun rememberAnnouncementCoordinator(
    viewModel: AnnouncementViewModel = hiltViewModel()
): AnnouncementCoordinator {
    return remember(viewModel) {
        AnnouncementCoordinator(
            viewModel = viewModel
        )
    }
}