package com.saegil.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_token")
data class TokenEntity(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "access_token") val accessToken: String?,
    @ColumnInfo(name = "refresh_token") val refreshToken: String?
)
