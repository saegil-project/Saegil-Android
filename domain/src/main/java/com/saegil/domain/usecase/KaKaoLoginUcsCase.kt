package com.saegil.domain.usecase

import android.content.Context
import com.saegil.domain.repository.AuthRepository
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(context: Context): Boolean {
        return authRepository.login(context)
    }
}
