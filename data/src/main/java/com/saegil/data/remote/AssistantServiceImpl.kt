package com.saegil.data.remote

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
import io.ktor.util.InternalAPI
import java.io.File
import javax.inject.Inject

class AssistantServiceImpl @Inject constructor(
    private val client: HttpClient
) : AssistantService {
    @OptIn(InternalAPI::class)
    override suspend fun getAssistant(file: File): UploadAudioDto {
        val response = client.post(HttpRoutes.ASSISTANT) {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            key = "file",
                            value = file.inputStream(),
                            headers = Headers.build {
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; name=\"file\"; filename=\"${file.name}\""
                                )
                                append(HttpHeaders.ContentType, "audio/x-m4a")
                            }
                        )
//                        append("thread_id", "") // 필요 시 추가
//                        append("provider", "OPENAI") // 필요 시 추가
                    }
                )
            )
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
            contentType(ContentType.MultiPart.FormData) // 이건 없어도 됩니다. 위에서 자동 설정됨
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
