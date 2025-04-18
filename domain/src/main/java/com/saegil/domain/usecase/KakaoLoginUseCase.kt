package com.saegil.domain.usecase

import com.saegil.domain.repository.OAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val oAuthRepository: OAuthRepository
) {
    suspend operator fun invoke(kakaoAccessToken: String): Flow<Boolean> {
        return oAuthRepository.loginWithKakao(kakaoAccessToken)
    }
}