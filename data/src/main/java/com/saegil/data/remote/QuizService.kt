package com.saegil.data.remote

import com.saegil.data.model.QuizDto

interface QuizService {

    suspend fun getQuiz(id: Long): QuizDto

}