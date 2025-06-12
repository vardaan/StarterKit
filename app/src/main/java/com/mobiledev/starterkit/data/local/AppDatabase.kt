package com.mobiledev.starterkit.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Entity
import androidx.room.PrimaryKey

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false   // set to true and supply a schema location if you need migrations
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

 @Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String
)

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clear()
}

