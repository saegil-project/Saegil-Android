package com.saegil.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saegil.data.model.TokenEntityDto

@Dao
interface TokenDao {

    @Query("SELECT * FROM token WHERE id = 0")
    suspend fun getToken(): TokenEntityDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: TokenEntityDto)

    @Query("DELETE FROM token")
    suspend fun clearToken()

}