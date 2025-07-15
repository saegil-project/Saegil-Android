package com.saegil.domain.repository

import com.saegil.domain.model.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    fun getQuiz(id: Long) : Flow<Quiz>

}