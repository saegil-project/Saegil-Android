package com.saegil.domain.model

data class Notice(
    val id: Long,
    val title: String,
    val content: String,
    val source: String,
    val date: String,
    val webLink: String
)
