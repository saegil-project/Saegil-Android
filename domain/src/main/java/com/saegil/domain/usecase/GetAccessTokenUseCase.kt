package com.saegil.domain.usecase

import com.saegil.domain.repository.OAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val repository: OAuthRepository
) {
    operator fun invoke(): Flow<String?> = flow {
        emit(repository.getToken().accessToken)
    }
}