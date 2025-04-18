package com.saegil.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saegil.domain.model.TokenEntity

@Entity(tableName = "token")
data class TokenEntityDto(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "access_token") val accessToken: String?,
    @ColumnInfo(name = "refresh_token") val refreshToken: String?
) {
    fun toDomain(): TokenEntity = TokenEntity(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}
