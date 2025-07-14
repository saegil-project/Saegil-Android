package com.saegil.data.repository

import com.saegil.data.remote.QuizService
import com.saegil.domain.model.Quiz
import com.saegil.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizService: QuizService
) : QuizRepository {

    override fun getQuiz(id: Long): Flow<Quiz> = flow {
        emit(quizService.getQuiz(id).toDomain())
    }.flowOn(Dispatchers.IO)

}