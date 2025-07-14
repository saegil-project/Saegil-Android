package com.saegil.domain.usecase

import com.saegil.domain.repository.QuizRepository
import javax.inject.Inject

class GetQuizUseCase @Inject constructor(
    private val repository: QuizRepository
) {

    operator fun invoke(id: Long) = repository.getQuiz(id)
}