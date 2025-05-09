package com.saegil.domain.repository

import com.saegil.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {

    fun getUserInfo(): Flow<UserInfo>

}