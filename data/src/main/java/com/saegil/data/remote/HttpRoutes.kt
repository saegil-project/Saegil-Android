package com.saegil.data.remote

import com.saegil.data.BuildConfig

object HttpRoutes {

    private const val BASE_URL = BuildConfig.BASE_URL

    const val NOTICES = "$BASE_URL/api/v1/notices?"

    const val ORGANIZATION = "$BASE_URL/api/v1/organizations/nearby" //근처 기관 조회

    const val OAUTH = "$BASE_URL/api/v1/oauth2/login/KAKAO"
}