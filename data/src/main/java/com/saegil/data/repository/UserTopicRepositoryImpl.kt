package com.saegil.data.repository

import com.saegil.data.remote.InterestService
import com.saegil.domain.model.Interest
import com.saegil.domain.repository.UserTopicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserTopicRepositoryImpl @Inject constructor(
    private val interestService: InterestService,
) : UserTopicRepository {

    private val preferredTopicsState = MutableStateFlow<List<Interest>>(emptyList())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val initial = interestService.getUserInterest().map { it.toDomain() }
            preferredTopicsState.emit(initial)
        }
    }

    override suspend fun savePreferredTopics(topics: List<Interest>) {
        interestService.setUserInterest(topics.map { it.type })
        val updated = interestService.getUserInterest().map { it.toDomain() }
        preferredTopicsState.emit(updated)
    }

    override fun getPreferredTopics(): Flow<List<Interest>> = preferredTopicsState

    override fun getCategories(): Flow<List<Interest>> = flow {
        emit(interestService.getInterest().map { it.toDomain() })
    }.flowOn(Dispatchers.IO)

}