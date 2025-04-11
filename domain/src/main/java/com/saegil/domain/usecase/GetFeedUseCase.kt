package com.saegil.domain.usecase

import androidx.paging.PagingData
import com.saegil.domain.model.Notice
import com.saegil.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {
    operator fun invoke(
        query: String?,
        organization: Int?,
    ) : Flow<PagingData<Notice>> =
        feedRepository.getFeeds(query, organization)
}