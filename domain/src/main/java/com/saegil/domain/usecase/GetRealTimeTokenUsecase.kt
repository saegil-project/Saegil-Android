package com.saegil.domain.usecase

import android.util.Log
import com.saegil.domain.model.Recruitment
import com.saegil.domain.repository.AssistantRepository
import com.saegil.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import java.lang.reflect.Constructor
import javax.inject.Inject

class GetRealTimeTokenUsecase @Inject constructor(
    private val assistantRepository: AssistantRepository
) {
    suspend operator fun invoke(): String {
       val token =  assistantRepository.getRealTimeApiToken()
        Log.d("token", token.toString())
        return token.toString()
    }

}

