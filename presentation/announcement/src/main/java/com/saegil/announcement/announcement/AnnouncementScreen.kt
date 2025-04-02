package com.saegil.announcement.announcement


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AnnouncementScreen(
    viewModel: AnnouncementViewModel = hiltViewModel()
) {

    val feedState by viewModel.feedUiState.collectAsStateWithLifecycle()

    AnnouncementScreen(
        feedState = feedState
    )


}

@Composable
internal fun AnnouncementScreen(
    feedState : AnnouncementUiState,
) {
    Column {

    }
}

@Composable
@Preview(name = "Announcement")
private fun AnnouncementScreenPreview() {
    AnnouncementScreen(
    )
}

