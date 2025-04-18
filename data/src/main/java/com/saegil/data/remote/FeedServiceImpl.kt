package com.saegil.data.remote


import android.util.Log
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
        sourceId: Long?,
        size: Int?,
        lastId: Long?
    ): NoticeListDto? {
        val urlBuilder = URLBuilder(HttpRoutes.NOTICES).apply {
            query?.let { parameters.append("query", it) }
            sourceId?.let { parameters.append("sourceId", it.toString()) }
            size?.let { parameters.append("size", it.toString()) }
            lastId?.let { parameters.append("lastId", it.toString()) }
        }
        Log.d("로그로그", "${urlBuilder.build()}")
        return try {
            client.get(urlBuilder.build()){
                headers {
                    accept(ContentType.Application.Json)
                }
            }.body<NoticeListDto>()
        } catch (e : Exception) {
            Log.d("FeedService", e.toString())
            null
        }
    }
}