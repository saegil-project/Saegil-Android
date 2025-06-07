package com.saegil.map.map

import com.saegil.domain.model.Organization
import com.saegil.domain.model.Recruitment


sealed interface MapUiState {

    data object Loading : MapUiState

    data class OrganizationSuccess(
        val organizationList: List<Organization>
    ) : MapUiState

    data class RecruitmentSuccess(
        val recruitmentList: List<Recruitment>
    ) : MapUiState
}