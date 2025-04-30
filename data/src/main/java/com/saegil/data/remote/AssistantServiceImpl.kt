package com.saegil.data.remote


import android.util.Log
import com.saegil.data.model.UploadAudioDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File
import javax.inject.Inject

class AssistantServiceImpl @Inject constructor(
    private val client: HttpClient
) : AssistantService {

    override suspend fun getAssistant(file: File): UploadAudioDto? {
        return try {
//            val requestFile = file.asRequestBody("audio/mp3".toMediaTypeOrNull())
//            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val fileBytes = file.readBytes()

            client.post(HttpRoutes.ASSISTANT) {
                headers {
                    bearerAuth("saegil-dev-test-token")
                }
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                key = "file",
                                value = fileBytes,
                                headers = Headers.build {
                                    append(HttpHeaders.ContentType, "audio/mp3")
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "form-data; name=\"file\"; filename=\"${file.name}\""
                                    )
                                }
                            )
                        }
                    )
                )
            }.body<UploadAudioDto>()
//            client.post(HttpRoutes.ASSISTANT) {
//                headers {
//                    "saegil-dev-test-token"
//                }
//                contentType(ContentType.MultiPart.FormData)
//                setBody(
//                    MultiPartFormDataContent(
//                        formData {
//                            append(
//                                key = "file",
//                                value = file.boundary,
////                            file.readBytes(),
//                                headers = Headers.build {
//                                    append(
//                                        HttpHeaders.ContentDisposition,
//                                        "filename=${file.contentType}"
//                                    )
//                                }
//                            )
//                        }
//                    )
//                )
//            }.body<UploadAudioDto>()
        } catch (e: Exception) {
            Log.d("AssistantService", e.toString())
            null
        }
    }
}