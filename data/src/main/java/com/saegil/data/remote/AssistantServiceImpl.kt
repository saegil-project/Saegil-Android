package com.saegil.data.remote

import com.saegil.data.local.ThreadPreferencesManager
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
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject

class AssistantServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val threadPreferencesManager: ThreadPreferencesManager
) : AssistantService {
    override suspend fun getAssistant(file: File): UploadAudioDto? {
        val threadId = threadPreferencesManager.threadId.first()
        
        val response = client.post(HttpRoutes.ASSISTANT) {
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
                        threadId?.let { id ->
                            append("thread_id", id)
                        }
                    }
                )
            )
            headers {
                append(HttpHeaders.Authorization, "Bearer saegil-dev-test-token")
                append(HttpHeaders.Accept, "application/json")
            }
            contentType(ContentType.MultiPart.FormData)
        }
        return response.body()
    }
}


//            client.post(HttpRoutes.ASSISTANT) {
//                headers {
//                    append(HttpHeaders.Accept, "application/json")
//                    append(
//                        HttpHeaders.Authorization,
//                        "Bearer saegil-dev-test-token"
//                    ) // TODO: 실제 토큰 주입
//                }
//                contentType(ContentType.MultiPart.FormData)
//                setBody(
//                    MultiPartFormDataContent(
//                        formData {
//                            append(
//                                key = "file",
//                                value = file.readBytes(),
//                                headers = Headers.build {
//                                    append(
//                                        HttpHeaders.ContentDisposition,
//                                        "form-data; name=\"file\"; filename=\"${file.name}\""
//                                    )
//                                    append(HttpHeaders.ContentType, "audio/x-m4a")
//                                }
//                            )
//                        }
//                    )
//                )
//            }.body<UploadAudioDto>()
//        } catch (e: Exception) {
//            Log.e("AssistantService", "Error uploading file: ${e.message}", e)
//
//
//            null
//        }
//    }
