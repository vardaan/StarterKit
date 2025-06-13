package com.mobiledev.starterkit.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Entity

@Database(
    entities = [],
    version = 1,
    exportSchema = false   // set to true and supply a schema location if you need migrations
)
abstract class AppDatabase : RoomDatabase() {
}
