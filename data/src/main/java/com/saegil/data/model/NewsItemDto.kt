package com.saegil.data.model

import com.saegil.domain.model.NewsItem
import kotlinx.serialization.Serializable

@Serializable
data class NewsItemDto(
    val id: Long,
    val title: String,
    val category: String,
    val thumbnailUrl: String,
    val date: String,
) {

    fun toDomain() = NewsItem(
        id = id,
        title = title,
        category = category,
        thumbnailUrl = thumbnailUrl,
        date = date
    )

}
