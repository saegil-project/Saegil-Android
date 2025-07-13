package com.saegil.data.repository

import com.saegil.data.local.UserPreferenceDataStore
import com.saegil.domain.repository.UserTopicRepository
import javax.inject.Inject

class UserTopicRepositoryImpl @Inject constructor(
    private val dataStore: UserPreferenceDataStore
) : UserTopicRepository {

    override suspend fun savePreferredTopics(topics: List<String>) {
        dataStore.savePreferredTopics(topics)
    }

    override fun getPreferredTopics() = dataStore.getPreferredTopics()
}