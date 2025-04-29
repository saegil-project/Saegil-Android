package com.saegil.domain.usecase

import com.saegil.domain.model.Token
import com.saegil.domain.repository.OAuthRepository
import javax.inject.Inject

class WithdrawalUseCase @Inject constructor(
    private val oAuthRepository: OAuthRepository
) {
    suspend operator fun invoke(token: Token) = oAuthRepository.requestWithdrawal(token.accessToken, token.refreshToken)
}