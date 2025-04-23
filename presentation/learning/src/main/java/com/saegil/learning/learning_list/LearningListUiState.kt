package com.saegil.learning.learning_list

import com.saegil.domain.model.Scenario

sealed interface LearningListUiState {

    data object Loading : LearningListUiState

    data class Success(
        val organizationList: List<Scenario>
    ) : LearningListUiState

}