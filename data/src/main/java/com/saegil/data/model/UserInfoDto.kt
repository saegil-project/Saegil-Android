package com.saegil.data.model

import com.saegil.domain.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    val id: Long,
    val name: String,
    val profileImage: String,
) {
    fun toDomain() = UserInfo(
        id = id,
        name = name,
        profileImage = profileImage
    )
}
