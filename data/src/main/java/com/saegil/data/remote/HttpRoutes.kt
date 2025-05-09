package com.saegil.data.remote

import com.saegil.data.BuildConfig

object HttpRoutes {

    private const val BASE_URL = BuildConfig.BASE_URL

    const val NOTICES = "$BASE_URL/api/v1/notices?"

    const val ORGANIZATION = "$BASE_URL/api/v1/organizations/nearby" //근처 기관 조회

    const val OAUTH_LOGIN = "$BASE_URL/api/v1/oauth2/login/KAKAO"

    const val OAUTH_LOGOUT = "$BASE_URL/api/v1/oauth2/logout"

    const val OAUTH_WITHDRAWAL = "$BASE_URL/api/v1/oauth2/withdrawal"

    const val OAUTH_VALIDATE_TOKEN = "$BASE_URL/api/v1/oauth2/validate-token"

    const val SCENARIO = "$BASE_URL/api/v1/scenarios" //시뮬레이션 상황 목록 조회

    const val ASSISTANT = "$BASE_URL/api/v1/llm/assistant/upload" // 음성 파일로부터 Assistant 응답 가져오기
  
    const val SIMULATION_LOG = "$BASE_URL/api/v1/simulations"

    const val USER = "$BASE_URL/api/v1/users/me"

}