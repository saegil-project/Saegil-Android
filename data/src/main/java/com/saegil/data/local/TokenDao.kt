package com.saegil.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saegil.data.model.TokenEntity

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: TokenEntity)

    @Query("SELECT * FROM user_token WHERE id = 1 LIMIT 1")
    suspend fun getToken(): TokenEntity?

    @Query("DELETE FROM user_token WHERE id = 1")
    suspend fun deleteToken()
}