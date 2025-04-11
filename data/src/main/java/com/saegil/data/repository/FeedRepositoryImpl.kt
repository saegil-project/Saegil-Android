package com.saegil.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saegil.data.remote.FeedService
import com.saegil.domain.model.Notice
import com.saegil.domain.repository.FeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedService: FeedService
) : FeedRepository {

    override fun getFeeds(
        query: String?,
        organization: Int?,
    ): Flow<PagingData<Notice>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 3 // 3개남으면 추가로드함
            ),
            pagingSourceFactory = { FeedPagingSource(
                feedService = feedService,
                query = query,
                sourceId = organization,
            ) } // Paging은 내부적으로 데이터가 갱신될 떄마다 PagingSource를 새로 생성해서 사용해서 이를 위한 매번 인스턴스를 새로 생성할 람다가 필요함
        ).flow.flowOn(Dispatchers.IO)  //데이터 요청은 IO
    }

}