package com.saegil.data.repository

import com.saegil.data.model.NewsResource
import com.saegil.data.remote.FeedService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val feedService: FeedService
) {

    fun getFeeds(): Flow<List<NewsResource>> = flow {
        emit(feedService.getFeeds())
    }.flowOn(Dispatchers.IO) //데이터 요청은 IO

}