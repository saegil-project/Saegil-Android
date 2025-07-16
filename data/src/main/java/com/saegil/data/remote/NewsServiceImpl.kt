package com.saegil.data.remote

import android.util.Log
import com.saegil.data.model.NewsItemDto
import com.saegil.data.remote.HttpRoutes.NEWS
import com.saegil.data.remote.HttpRoutes.TEST
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import javax.inject.Inject

class NewsServiceImpl @Inject constructor(
    private val client: HttpClient
): NewsService {

    override suspend fun getNewsByTopics(): List<NewsItemDto> {
        val testResponse = client.post(TEST) {
            parameter("title", "테스트 알림")
            parameter("body", "내용입니다.")
        }
        Log.d("경로",testResponse.toString())
        Log.d("경로",testResponse.body())
        return client.get(NEWS).body()
    }

}