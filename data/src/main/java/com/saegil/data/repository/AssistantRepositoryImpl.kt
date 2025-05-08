package com.saegil.data.repository


import com.saegil.data.remote.AssistantApi
import com.saegil.domain.model.UploadAudio
import com.saegil.domain.repository.AssistantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AssistantRepositoryImpl(
    private val api: AssistantApi
) : AssistantRepository {

    override suspend fun uploadAudio(file: File): Flow<Result<UploadAudio>> = flow {
        try {
            val requestFile = file.asRequestBody("audio/x-m4a".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val response = api.uploadAudio(body)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it.toDomain()))
                } ?: emit(Result.failure(Exception("Empty body")))
            } else {
                emit(
                    Result.failure(
                        Exception(
                            "Error: ${response.code()} - ${
                                response.errorBody()?.string()
                            }"
                        )
                    )
                )
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
