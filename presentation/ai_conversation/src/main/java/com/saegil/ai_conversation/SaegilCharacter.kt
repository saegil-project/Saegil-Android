package com.saegil.ai_conversation

import com.saegil.ai_conversation.R

enum class SaegilCharacter(
    val img: Int,
    val nickname: String,
    val gender: String,
    val personality: String,
    val description: String
) {
    SAEROM(
        R.drawable.img_saerom,
        "새롬",
        "여",
        "ENFP",
        "동갑내기 친구"
    ),
    GILDONG(
        R.drawable.img_gildong,
        "길동",
        "남",
        "INTJ",
        "연장자"
    )
}