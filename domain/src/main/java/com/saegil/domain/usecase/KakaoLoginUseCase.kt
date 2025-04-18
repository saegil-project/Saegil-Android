package com.saegil.domain.usecase

import com.saegil.domain.repository.OAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val oAuthRepository: OAuthRepository
) {
    operator fun invoke(authCode: String): Flow<Boolean> = flow {
        emit(oAuthRepository.loginWithKakao(authCode))
    }
}