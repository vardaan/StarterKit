package com.mobiledev.starterkit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Entity
import androidx.room.PrimaryKey

@Database(
    entities = [DummyEntity::class],
    version = 1,
    exportSchema = false   // set to true and supply a schema location if you need migrations
)
abstract class AppDatabase : RoomDatabase() {
}

@Entity
data class DummyEntity(@PrimaryKey val id: Long)
