package com.saegil.data.remote

import com.saegil.data.model.NoticeListDto

interface FeedService {

    suspend fun getFeeds(
        query: String? = null,
        sourceId: Long? = null,
        size: Int? = null,
        lastId: Long? = null
    ) : NoticeListDto?

}