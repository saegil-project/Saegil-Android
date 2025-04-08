package com.saegil.domain.repository

import com.saegil.domain.model.Notice
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getFeeds(): Flow<List<Notice>>

}