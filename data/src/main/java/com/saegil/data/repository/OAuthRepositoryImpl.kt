package com.saegil.data.repository

import com.saegil.data.local.TokenDataSource
import com.saegil.data.model.JwtResponse
import com.saegil.data.remote.HttpRoutes.OAUTH
import com.saegil.domain.repository.OAuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OAuthRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val tokenDataSource: TokenDataSource
) : OAuthRepository {
    override suspend fun loginWithKakao(kakaoAccessToken: String): Flow<Boolean> = flow {
        val response: JwtResponse = client.post(OAUTH) {
            contentType(ContentType.Application.Json)
            setBody(mapOf("accessToken" to kakaoAccessToken))
        }.body()

        tokenDataSource.saveAccessToken(response.accessToken)
        tokenDataSource.saveRefreshToken(response.refreshToken)

        emit(true)
    }.catch {
        emit(false)
    }
}
