package com.saegil.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResource(
    val title: String,
    val date: String,
    val organization: String,
    val url: String
)
