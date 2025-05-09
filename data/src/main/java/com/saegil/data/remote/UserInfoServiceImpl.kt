package com.saegil.data.remote

import com.saegil.data.model.UserInfoDto
import com.saegil.data.remote.HttpRoutes.USER
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class UserInfoServiceImpl @Inject constructor(
    private val client: HttpClient
) : UserInfoService {

    override suspend fun getUserInfo(): UserInfoDto {
        return client.get(USER).body()
    }

}