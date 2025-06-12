package com.mobiledev.starterkit.data.local

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun addHabit(name: String)

    fun getHabits(): Flow<List<Habit>>

    suspend fun setHabitCompleted(habit: Habit)
}
