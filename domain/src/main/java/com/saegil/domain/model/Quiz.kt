package com.saegil.domain.model

data class Quiz(
    val id: Long,
    val title: String,
    val category: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val date: String,
    val question: String,
    val answer: String,
    val explanation: String,
)