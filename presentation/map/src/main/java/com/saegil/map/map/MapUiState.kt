package com.saegil.map.map

import com.saegil.domain.model.Organization


sealed class MapState {
    data class MapList(val organizations: List<Organization>) : MapState()
}