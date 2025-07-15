package com.saegil.ai_conversation

import com.saegil.ai_conversation.R

enum class SaegilCharacter(
    val img: Int,
    val nickname: String,
    val gender: String,
    val personality: String,
    val description: String,
    val comment: String,
) {
    SAEROM(
        R.drawable.img_saerom,
        "새롬",
        "여",
        "ENFP",
        "동갑내기 친구",
        "동갑내기 친구와 “반말”로 편안하게 대화해보세요!"
    ),
    GILDONG(
        R.drawable.img_gildong,
        "길동",
        "남",
        "INTJ",
        "연장자",
        "연장자에게는 “존댓말”로 대화하는 걸 잊지마세요!"
    )
}