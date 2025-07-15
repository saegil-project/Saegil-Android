package com.saegil.data.remote

import com.saegil.data.model.NewsItemDto
import com.saegil.data.remote.HttpRoutes.NEWS
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class NewsServiceImpl @Inject constructor(
    private val client: HttpClient
): NewsService {

    override suspend fun getNewsByTopics(): List<NewsItemDto> {
        return client.get(NEWS).body()
    }

}