package com.saegil.data.remote

import com.saegil.data.model.TokenEntityDto
import com.saegil.data.model.ValidateTokenResponse
import com.saegil.data.remote.HttpRoutes.OAUTH_LOGIN
import com.saegil.data.remote.HttpRoutes.OAUTH_VALIDATE_TOKEN
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

class OAuthServiceImpl @Inject constructor(
    private val client: HttpClient
) : OAuthService {

    override suspend fun loginWithKakao(authCode: String): TokenEntityDto {
        val response = client.post(OAUTH_LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(mapOf("authorizationCode" to authCode))
        }
        val json = response.body<JsonObject>()
        return TokenEntityDto(
            accessToken = json["accessToken"]!!.jsonPrimitive.content,
            refreshToken = json["refreshToken"]!!.jsonPrimitive.content
        )
    }

    override suspend fun validateAccessToken(accessToken: String): ValidateTokenResponse {
        return client.get(OAUTH_VALIDATE_TOKEN) {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }.body()
    }
}