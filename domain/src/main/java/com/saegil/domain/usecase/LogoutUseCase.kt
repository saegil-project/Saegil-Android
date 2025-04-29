package com.saegil.domain.usecase

import com.saegil.domain.model.Token
import com.saegil.domain.repository.OAuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val oAuthRepository: OAuthRepository
) {
    suspend operator fun invoke(token: Token) = oAuthRepository.requestLogout(token.accessToken, token.refreshToken)
}