package com.saegil.data.repository

import android.content.Context
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

    override suspend fun login(context: Context): Boolean = suspendCancellableCoroutine { cont ->
        if (userApiClient.isKakaoTalkLoginAvailable(context)) {
            userApiClient.loginWithKakaoTalk(context) { token, error ->
                cont.resume(token != null && error == null)
            }
        } else {
            userApiClient.loginWithKakaoAccount(context) { token, error ->
                cont.resume(token != null && error == null)
            }
        }
    }
}