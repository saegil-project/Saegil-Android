package com.saegil.data.repository

import android.util.Log
import com.saegil.data.remote.FeedService
import com.saegil.domain.model.Notice
import com.saegil.domain.repository.FeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedService: FeedService
) : FeedRepository {

    override fun getFeeds(): Flow<List<Notice>> = flow {
        feedService.getFeeds(
            size = 10
        )?.notices?.let {
            Log.d("잘되나요", "${it.size}")
            emit(it.map { dto -> dto.toDomain() })
        }
    }.flowOn(Dispatchers.IO) //데이터 요청은 IO

}