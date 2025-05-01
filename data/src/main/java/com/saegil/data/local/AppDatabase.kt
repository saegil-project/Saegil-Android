package com.saegil.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = ScenarioDto)
abstract class AppDatabase : RoomDatabase() {


}