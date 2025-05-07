package com.saegil.data.remote

import android.util.Log
import com.saegil.data.model.UploadAudioDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import java.io.File
import javax.inject.Inject

class AssistantServiceImpl @Inject constructor(
    private val client: HttpClient
) : AssistantService {

    override suspend fun getAssistant(file: File): UploadAudioDto? {
        return try {
            client.post(HttpRoutes.ASSISTANT) {
                headers {
                    append(HttpHeaders.Accept, "application/json")
                    append(
                        HttpHeaders.Authorization,
                        "Bearer saegil-dev-test-token"
                    ) // TODO: 실제 토큰 주입
                }
                contentType(ContentType.MultiPart.FormData)
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                key = "file",
                                value = file.readBytes(),
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "form-data; name=\"file\"; filename=\"${file.name}\""
                                    )
                                    append(HttpHeaders.ContentType, "audio/x-m4a")
                                }
                            )
                        }
                    )
                )
            }.body<UploadAudioDto>()
        } catch (e: Exception) {
            Log.e("AssistantService", "Error uploading file: ${e.message}", e)

//            MultiPartFormDataContent(
//                formData {
//                    append(
//                        key = "file",
//                        value = file.readBytes(),
//                        headers = Headers.build {
//                            append(HttpHeaders.ContentDisposition, "form-data; name=\"file\"; filename=\"${file.name}\"")
//                            append(HttpHeaders.ContentType, "audio/x-m4a")
//                        }
//                    )
//                }
//            )
//        }
            null
        }
    }
}