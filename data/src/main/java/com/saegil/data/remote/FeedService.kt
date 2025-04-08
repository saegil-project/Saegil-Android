package com.saegil.data.remote

import com.saegil.data.model.NoticeResponse

interface FeedService {

    suspend fun getFeeds(
        query: String? = null,
        sourceId: String? = null,
        size: Int? = null,
        lastId: Long? = null
    ) : NoticeResponse?

}