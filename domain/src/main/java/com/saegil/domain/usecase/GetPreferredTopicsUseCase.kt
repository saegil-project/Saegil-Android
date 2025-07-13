package com.saegil.domain.usecase

import com.saegil.domain.repository.UserTopicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferredTopicsUseCase @Inject constructor(
    private val repository: UserTopicRepository
) {
    operator fun invoke(): Flow<List<String>> = repository.getPreferredTopics()
}