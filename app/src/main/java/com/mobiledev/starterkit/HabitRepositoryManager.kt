package com.mobiledev.starterkit

import com.mobiledev.starterkit.data.local.Habit
import com.mobiledev.starterkit.data.local.HabitDao
import com.mobiledev.starterkit.data.local.HabitEntity
import com.mobiledev.starterkit.data.local.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HabitRepositoryManager @Inject constructor(private val habitDao: HabitDao) : HabitRepository {
    @OptIn(ExperimentalTime::class)
    override suspend fun addHabit(name: String) = habitDao.insertHabit(
        HabitEntity(
            name = name, createdAt = Clock.System.now().toEpochMilliseconds()
        )
    )

    override fun getHabits(): Flow<List<Habit>> = habitDao.observeAllDomain()

    override suspend fun setHabitCompleted(habit: Habit) =
        habitDao.setHabitCompleted(habit.id, habit.isCompleted)
}
