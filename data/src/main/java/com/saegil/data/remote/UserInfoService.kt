package com.saegil.data.remote

import com.saegil.data.model.UserInfoDto

interface UserInfoService {

    suspend fun getUserInfo(): UserInfoDto

}