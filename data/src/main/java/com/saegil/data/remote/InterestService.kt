package com.saegil.data.remote

import com.saegil.data.model.InterestDto

interface InterestService {

    suspend fun getUserInterest(): List<InterestDto>

    suspend fun setUserInterest(interests: List<String>) : Boolean

    suspend fun getInterest(): List<InterestDto>

}