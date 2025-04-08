package com.saegil.domain.repository

import androidx.paging.PagingData
import com.saegil.domain.model.Notice
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getFeeds(): Flow<PagingData<Notice>>

}