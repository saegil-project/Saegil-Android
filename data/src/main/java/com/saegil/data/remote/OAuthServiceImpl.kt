package com.saegil.data.remote

import com.example.app.data.proto.TokenProto
import com.saegil.data.model.ValidateTokenResponse
import com.saegil.data.remote.HttpRoutes.OAUTH_LOGIN
import com.saegil.data.remote.HttpRoutes.OAUTH_LOGOUT
import com.saegil.data.remote.HttpRoutes.OAUTH_VALIDATE_TOKEN
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

class OAuthServiceImpl @Inject constructor(
    private val client: HttpClient
) : OAuthService {

    override suspend fun loginWithKakao(accessToken: String): TokenProto {
        val response = client.post(OAUTH_LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(mapOf("accessToken" to accessToken))
        }
        val json = response.body<JsonObject>()
        return TokenProto.newBuilder()
            .setAccessToken(json["accessToken"]!!.jsonPrimitive.content)
            .setRefreshToken(json["refreshToken"]!!.jsonPrimitive.content)
            .build()
    }

    override suspend fun validateAccessToken(accessToken: String): ValidateTokenResponse {
        return client.get(OAUTH_VALIDATE_TOKEN) {
            header(HttpHeaders.Authorization, accessToken)
        }.body()
    }

    override suspend fun requestLogout(tokenProto: TokenProto): Boolean {
        val response = client.post(OAUTH_LOGOUT) {
            header(HttpHeaders.Authorization, tokenProto.accessToken)
            contentType(ContentType.Application.Json)
            setBody(mapOf("refreshToken" to tokenProto.refreshToken))
        }
        return response.status == HttpStatusCode.NoContent
    }
}