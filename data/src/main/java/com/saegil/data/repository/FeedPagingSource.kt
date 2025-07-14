package com.saegil.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saegil.data.remote.FeedService
import com.saegil.domain.model.Notice

class FeedPagingSource(
    private val feedService: FeedService,
    private val query: String? = null,
    private val pageSize: Int = 10
) : PagingSource<Long, Notice>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Notice> {
        val lastId = params.key
        return try {
            val response = feedService.getFeeds(
                query = query,
                size = pageSize,
                lastId = lastId
            )
            if (response == null) {
                LoadResult.Error(Exception(""))
            } else {
                val notices = response.notices.map { it.toDomain() }
                val nextKey = if (response.hasNext && notices.isNotEmpty()) {
                    notices.last().id
                } else {
                    null
                }

                LoadResult.Page(
                    data = notices,
                    prevKey = null,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, Notice>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}