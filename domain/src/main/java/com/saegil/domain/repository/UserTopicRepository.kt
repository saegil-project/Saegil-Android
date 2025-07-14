package com.saegil.domain.repository

import com.saegil.domain.model.Interest
import kotlinx.coroutines.flow.Flow

interface UserTopicRepository {
    suspend fun savePreferredTopics(topics: List<Interest>)
    fun getPreferredTopics(): Flow<List<Interest>>
    fun getCategories(): Flow<List<Interest>>
}