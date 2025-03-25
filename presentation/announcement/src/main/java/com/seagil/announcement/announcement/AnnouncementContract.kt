package com.seagil.announcement.announcement


/**
 * UI State that represents AnnouncementScreen
 **/
class AnnouncementState

/**
 * Announcement Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class AnnouncementActions(
    val onClick: () -> Unit = {}
)


