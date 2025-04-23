package com.saegil.data.remote


import android.util.Log
import com.saegil.data.model.ScenarioDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import javax.inject.Inject

class ScenarioServiceImpl @Inject constructor(
    private val client: HttpClient
) : ScenarioService {

    override suspend fun getScenarios(
    ): List<ScenarioDto>? {
        val urlBuilder = URLBuilder(HttpRoutes.SCENARIO)

        return try {
            client.get(urlBuilder.build()) {
                headers {
                    accept(ContentType.Application.Json)
                }
            }.body<List<ScenarioDto>>()
        } catch (e: Exception) {
            Log.d("FeedService", e.toString())
            null
        }
    }
}