package com.saegil.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {

    fun getUserName(): Flow<String>

}