package com.saegil.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saegil.data.model.TokenEntity

@Database(entities = [TokenEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao
}