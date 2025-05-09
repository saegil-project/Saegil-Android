package com.saegil.data.repository

import com.saegil.data.remote.UserInfoService
import com.saegil.domain.model.UserInfo
import com.saegil.domain.repository.UserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userInfoService: UserInfoService
) : UserInfoRepository {

    override fun getUserInfo(): Flow<UserInfo> = flow {
        emit(userInfoService.getUserInfo().toDomain())
    }.flowOn(Dispatchers.IO)

}