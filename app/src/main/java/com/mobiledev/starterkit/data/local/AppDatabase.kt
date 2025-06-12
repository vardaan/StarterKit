package com.mobiledev.starterkit.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Database(
    entities = [HabitEntity::class],
    version = 1,
    exportSchema = false   // set to true and supply a schema location if you need migrations
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Query("SELECT * FROM HabitEntity")
    fun observeAll(): Flow<List<HabitEntity>>

    @Query("UPDATE HabitEntity SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun setHabitCompleted(id: Int, isCompleted: Boolean)

    fun observeAllDomain(): Flow<List<Habit>> =
        observeAll().map { entities -> entities.map { it.toDomain() } }
}

@Entity
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val createdAt: Long,
    val isCompleted: Boolean = false,
)

fun HabitEntity.toDomain() = Habit(id, name, createdAt, isCompleted)

fun HabitEntity.fromDomain(habit: Habit) =
    HabitEntity(habit.id, habit.name, habit.createdAt, habit.isCompleted)

