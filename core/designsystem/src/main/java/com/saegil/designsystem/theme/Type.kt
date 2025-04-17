package com.saegil.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.saegil.designsystem.R

val NanumGothic = FontFamily(
    Font(resId = R.font.nanumgothic, weight = FontWeight.Normal)
)

val Typography = Typography()

val Typography.h1: TextStyle
    get() = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 24.sp
    )

val Typography.h2: TextStyle
    get() = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 18.sp
    )

val Typography.h3: TextStyle
    get() = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp
    )

val Typography.body1: TextStyle
    get() = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    )

val Typography.body2: TextStyle
    get() = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )

val Typography.caption: TextStyle
    get() = TextStyle(
        fontFamily = NanumGothic,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 14.sp
    )