package com.saegil.domain.usecase

import com.saegil.domain.repository.UserTopicRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val repository : UserTopicRepository
){

    operator fun invoke() = repository.getCategories()

}