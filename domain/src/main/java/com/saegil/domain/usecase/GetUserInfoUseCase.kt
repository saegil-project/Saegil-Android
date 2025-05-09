package com.saegil.domain.usecase

import com.saegil.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetUserNameInfoUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {

    operator fun invoke() = userInfoRepository.getUserName()

}