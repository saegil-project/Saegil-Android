package com.saegil.map.map

import com.saegil.domain.model.Organization


sealed interface MapUiState {

    data object Loading : MapUiState

    data class Success(
        val organizationList: List<Organization>
    ) : MapUiState

}