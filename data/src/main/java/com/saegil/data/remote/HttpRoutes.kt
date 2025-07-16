package com.saegil.data.remote

import com.saegil.data.BuildConfig

object HttpRoutes {

    private const val BASE_URL = BuildConfig.BASE_URL

    const val NOTICES = "$BASE_URL/api/v1/notices?"

    const val FACILITY = "$BASE_URL/api/v1/facilities/nearby" //근처 시설 조회

    const val RECRUITMENT = "$BASE_URL/api/v1/recruitments/nearby"

    const val OAUTH_LOGIN = "$BASE_URL/api/v1/oauth2/login/KAKAO"

    const val OAUTH_LOGOUT = "$BASE_URL/api/v1/oauth2/logout"

    const val OAUTH_WITHDRAWAL = "$BASE_URL/api/v1/oauth2/withdrawal"

    const val OAUTH_VALIDATE_TOKEN = "$BASE_URL/api/v1/oauth2/validate-token"

    const val SCENARIO = "$BASE_URL/api/v1/scenarios" //시뮬레이션 상황 목록 조회

    const val ASSISTANT = "$BASE_URL/api/v2/llm/assistant/upload" // 음성 파일로부터 Assistant 응답 가져오기

    const val TTS = "$BASE_URL/api/v1/llm/tts"
  
    const val SIMULATION_LOG = "$BASE_URL/api/v1/simulations"

    const val USER = "$BASE_URL/api/v1/users/me"

    const val NEWS = "$BASE_URL/api/v1/news"

    const val NEWS_INTERESTS = "$BASE_URL/api/v1/news/interests"

    const val NEWS_CATEGORIES = "$BASE_URL/api/v1/news/categories"

    const val TEST = "$BASE_URL/api/v1/notifications/test"

}