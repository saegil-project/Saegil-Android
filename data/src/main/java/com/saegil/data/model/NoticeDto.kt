package com.saegil.data.model

import com.saegil.domain.model.Notice
import kotlinx.serialization.Serializable

@Serializable
data class NoticeDto(
    val id: Long,
    val title: String,
    val content: String,
    val source: String,
    val date: String,
    val webLink: String
) {
    fun toDomain(): Notice {
        return Notice(
            id = id,
            title = title,
            content = content,
            source = source,
            date = date,
            webLink = webLink
        )
    }
}

@Serializable
data class NoticeListDto(
    val notices: List<NoticeDto>,
    val hasNext: Boolean
)

