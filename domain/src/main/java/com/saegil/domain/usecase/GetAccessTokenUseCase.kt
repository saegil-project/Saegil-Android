package com.saegil.domain.usecase

import com.saegil.domain.model.Token
import com.saegil.domain.repository.OAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: OAuthRepository
) {
    operator fun invoke(): Flow<Token?> = flow {
        emit(repository.getToken())
    }
}