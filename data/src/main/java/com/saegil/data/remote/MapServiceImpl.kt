package com.saegil.data.remote


import android.util.Log
import com.saegil.data.model.OrganizationDto
import com.saegil.data.model.RecruitmentDto
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

    override suspend fun getNearbyRecruitments(
        latitude: Double?,
        longitude: Double?,
        radius: Int?
    ): List<RecruitmentDto>? {
        Log.d("경로", "서비스")
        val urlBuilder = URLBuilder(HttpRoutes.RECRUITMENT).apply {
            latitude?.let { parameters.append("latitude", it.toString()) }
            longitude?.let { parameters.append("longitude", it.toString()) }
            radius?.let { parameters.append("radius", it.toString()) }
        }
        return try {
            val x=client.get(urlBuilder.build()) {
                headers {
                    accept(ContentType.Application.Json)
                }
            }.body<List<RecruitmentDto>?>()
            Log.d("경로", "굳")
            x
        } catch (e: Exception) {
            Log.d("경로", "터짐 ${e.toString()}")
            null
        }
    }
}