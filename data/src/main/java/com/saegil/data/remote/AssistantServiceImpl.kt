package com.saegil.data.remote

import com.saegil.data.model.UploadAudioDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.parameter
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File
import javax.inject.Inject

class AssistantServiceImpl @Inject constructor(
    private val client: HttpClient
) : AssistantService {

    override suspend fun getAssistant(file: File, threadId: String?, scenarioId: Int): UploadAudioDto {
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
}
