package com.saegil.data.remote

import com.saegil.data.model.NewsResource
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import javax.inject.Inject

class FeedServiceImpl @Inject constructor(
    private val client: HttpClient
) : FeedService {

    override suspend fun getFeeds(): List<NewsResource> {
        return try {
            client.get {
                url(HttpRoutes.POSTS)
            }.body()
        } catch (e : Exception) {
            emptyList()
        }
    }
}