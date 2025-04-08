package com.saegil.data.remote

import com.saegil.data.BuildConfig

object HttpRoutes {

    private const val BASE_URL = BuildConfig.BASE_URL

    const val NOTICES = "$BASE_URL/api/v1/notices?"

}