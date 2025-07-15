package com.saegil.data.model

import com.saegil.domain.model.Quiz
import kotlinx.serialization.Serializable

@Serializable
data class QuizDto (
    val id: Long,
    val title: String,
    val category: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val date: String,
    val question: String,
    val answer: String,
    val explanation: String,
) {
    fun toDomain() = Quiz(
        id = id,
        title = title,
        category = category,
        videoUrl = videoUrl,
        thumbnailUrl = thumbnailUrl,
        date = date,
        question = question,
        answer = answer,
        explanation = explanation,
    )
}