package com.saegil.announcement.announcement


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AnnouncementScreen(
    announcementViewModel: AnnouncementViewModel = hiltViewModel()
) {

    AnnouncementScreen(

    )

}

@Composable
internal fun AnnouncementScreen(

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

