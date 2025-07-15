package com.saegil.domain.usecase

import com.saegil.domain.repository.NewsRepository
import javax.inject.Inject

class GetTopicNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend operator fun invoke() = repository.getNewsByTopics()

}