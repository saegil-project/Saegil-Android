package com.saegil.data.model

import com.saegil.domain.model.NewsItem
import kotlinx.serialization.Serializable

@Serializable
data class NewsItemDto(
    val title: String,
    val topic: String,
    val date: String,
    val imageUrl: String,
) {

    fun toDomain() = NewsItem(
        title = title,
        topic = topic,
        date = date,
        imageUrl = imageUrl,
    )

}
