package com.saegil.data.repository

import com.kakao.sdk.user.UserApiClient
import com.saegil.domain.repository.AuthRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthRepositoryImpl @Inject constructor(
    private val userApiClient: UserApiClient
) : AuthRepository {
    override suspend fun isLoggedIn(): Boolean = suspendCancellableCoroutine { cont ->
        userApiClient.accessTokenInfo { tokenInfo, error ->
            cont.resume(error == null && tokenInfo != null)
        }
    }
}