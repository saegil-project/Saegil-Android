package com.saegil.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserTopicRepository {
    suspend fun savePreferredTopics(topics: List<String>)
    fun getPreferredTopics(): Flow<List<String>>
}