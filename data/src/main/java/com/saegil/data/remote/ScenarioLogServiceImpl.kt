package com.saegil.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class ScenarioLogServiceImpl @Inject constructor(
    private val client: HttpClient
) : ScenarioLogService {

    override suspend fun getScenarioLogList() {
        //return client.get(로그url).body() 이런 식으로 받아오겄제
    }

}