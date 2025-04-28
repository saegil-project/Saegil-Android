package com.saegil.domain.usecase

import com.saegil.domain.repository.OAuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val oAuthRepository: OAuthRepository
) {
    suspend operator fun invoke() = oAuthRepository.requestLogout()
}