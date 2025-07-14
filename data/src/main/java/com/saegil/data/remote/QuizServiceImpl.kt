package com.saegil.data.remote

import com.saegil.data.model.QuizDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class QuizServiceImpl @Inject constructor(
    private val client: HttpClient
): QuizService  {

    override suspend fun getQuiz(id: Long): QuizDto {
        return client.get(HttpRoutes.NEWS+"/$id/quiz").body()
    }
}