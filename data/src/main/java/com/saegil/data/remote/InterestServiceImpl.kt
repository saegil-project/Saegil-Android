package com.saegil.data.remote

import com.saegil.data.model.InterestDto
import com.saegil.data.remote.HttpRoutes.NEWS_CATEGORIES
import com.saegil.data.remote.HttpRoutes.NEWS_INTERESTS
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import javax.inject.Inject
import kotlin.collections.mapOf

class InterestServiceImpl @Inject constructor(
    private val client: HttpClient
): InterestService {

    override suspend fun getInterest(): List<InterestDto> {
        return client.get(NEWS_CATEGORIES).body()
    }

    override suspend fun getUserInterest(): List<InterestDto> {
        return client.get(NEWS_INTERESTS).body()
    }

    override suspend fun setUserInterest(interests: List<String>) : Boolean {
        val response = client.post(NEWS_INTERESTS) {
            setBody(mapOf("interests" to interests))
        }
        return response.status == HttpStatusCode.Created
    }

}