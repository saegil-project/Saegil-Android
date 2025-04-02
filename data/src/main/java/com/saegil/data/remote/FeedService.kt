package com.saegil.data.remote

import com.saegil.data.model.NewsResource

interface FeedService {

    suspend fun getFeeds() : List<NewsResource>

}