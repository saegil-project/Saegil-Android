package com.saegil.data.repository

import com.saegil.data.remote.AssistantService
import com.saegil.domain.model.UploadAudio
import com.saegil.domain.repository.AssistantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class AssistantRepositoryImpl(
    private val assistantService: AssistantService
) : AssistantRepository {

    override suspend fun uploadAudio(file: File): Flow<UploadAudio> = flow<UploadAudio> {
        assistantService.getAssistant(file).toDomain()
    }.flowOn(Dispatchers.IO)
//    override suspend fun uploadAudio(file: File): Flow<Result<UploadAudio>> = flow {
//        try {
//            val requestFile = file.asRequestBody("audio/x-m4a".toMediaTypeOrNull())
//            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
//            val response = api.uploadAudio(body)
//
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(Result.success(it.toDomain()))
//                } ?: emit(Result.failure(Exception("Empty body")))
//            } else {
//                emit(
//                    Result.failure(
//                        Exception(
//                            "Error: ${response.code()} - ${
//                                response.errorBody()?.string()
//                            }"
//                        )
//                    )
//                )
//            }
//        } catch (e: Exception) {
//            emit(Result.failure(e))
//        }
//    }
}
