package com.saegil.data.remote

import com.saegil.data.model.NoticeListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import javax.inject.Inject

class FeedServiceImpl @Inject constructor(
    private val client: HttpClient
) : FeedService {

    override suspend fun getFeeds(
        query: String?,
        size: Int?,
        lastId: Long?
    ): NoticeListDto? {
        val urlBuilder = URLBuilder(HttpRoutes.NOTICES).apply {
            query?.let { parameters.append("query", it) }
            size?.let { parameters.append("size", it.toString()) }
            lastId?.let { parameters.append("lastId", it.toString()) }
        }
        return try {
            client.get(urlBuilder.build()){
                headers {
                    accept(ContentType.Application.Json)
                }
            }.body<NoticeListDto>()
        } catch (e : Exception) {
            null
        }
    }
}