package com.saegil.domain.usecase

import com.saegil.domain.model.Interest
import com.saegil.domain.repository.UserTopicRepository
import javax.inject.Inject

class SavePreferredTopicsUseCase @Inject constructor(
    private val repository: UserTopicRepository
) {

    suspend operator fun invoke(topics: List<Interest>) = repository.savePreferredTopics(topics)

}