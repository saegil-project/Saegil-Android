package com.saegil.data.remote

import com.saegil.data.model.UploadAudioDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AssistantApi {
    @Multipart
    @POST("/api/v1/llm/assistant/upload")
    suspend fun uploadAudio(
        @Part file: MultipartBody.Part
    ): Response<UploadAudioDto>
}
