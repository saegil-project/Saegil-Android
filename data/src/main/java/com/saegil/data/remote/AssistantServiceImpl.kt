package com.saegil.data.remote

import android.util.Log
import com.saegil.data.model.OrganizationDto
import com.saegil.data.model.RealtimeResponse
import com.saegil.data.model.UploadAudioDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLBuilder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File
import javax.inject.Inject

class AssistantServiceImpl @Inject constructor(
    private val client: HttpClient
) : AssistantService {

    override suspend fun getAssistant(
        file: File,
        threadId: String?,
        scenarioId: Int
    ): UploadAudioDto {
        val response = client.submitFormWithBinaryData(
            url = HttpRoutes.ASSISTANT,
            formData = formData {
                append("file", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, "audio/*")
                    append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                })
            }
        ) {
            // thread_id가 있다면 쿼리 파라미터로 추가
            threadId?.let { parameter("thread_id", it) }
            parameter("scenario_id", scenarioId)
        }
        return response.body()
    }

    override suspend fun getRealtimeToken(): String? {

        val urlBuilder = URLBuilder(HttpRoutes.GET_REALTIME_TOKEN)

        val response = client.get(urlBuilder.build()) {
            headers {
                accept(ContentType.Application.Json)
            }
        }
        val responseBody = response.bodyAsText()
        val json = Json.parseToJsonElement(responseBody)

        // JSON 구조에서 client_secret.value 접근
        val value = json
            .jsonObject["client_secret"]
            ?.jsonObject
            ?.get("value")
            ?.jsonPrimitive
            ?.contentOrNull

        Log.d("value", value.toString())
        return value.toString()
    }

    override suspend fun realtimeAssistant() {
        TODO("Not yet implemented")
    }
}
