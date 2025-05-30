package com.saegil.data.remote

import com.saegil.data.model.UploadAudioDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.util.InternalAPI
import java.io.File
import javax.inject.Inject

class AssistantServiceImpl @Inject constructor(
    private val client: HttpClient
) : AssistantService {
    @OptIn(InternalAPI::class)
    override suspend fun getAssistant(file: File): UploadAudioDto {
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
            // parameter("thread_id", threadId)
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
