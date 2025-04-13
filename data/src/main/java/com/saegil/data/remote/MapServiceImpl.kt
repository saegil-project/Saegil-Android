package com.saegil.data.remote


import android.util.Log
import com.saegil.data.model.OrganizationDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import javax.inject.Inject

class MapServiceImpl @Inject constructor(
    private val client: HttpClient
) : MapService {

    override suspend fun getNearbyOrganizations(
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
    ): List<OrganizationDto>? {
        val urlBuilder = URLBuilder(HttpRoutes.ORGANIZATION).apply {
            latitude?.let { parameters.append("latitude", it.toString()) }
            longitude?.let { parameters.append("longitude", it.toString()) }
            radius?.let { parameters.append("radius", it.toString()) }
        }
        Log.d("로그로그", "${urlBuilder.build()}")
        return try {
            client.get(urlBuilder.build()) {
                headers {
                    accept(ContentType.Application.Json)
                }
            }.body<List<OrganizationDto>?>()
        } catch (e: Exception) {
            Log.d("MapService", e.toString())
            null
        }
    }
}