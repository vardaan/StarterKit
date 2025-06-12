package com.mobiledev.starterkit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledev.starterkit.data.local.Habit
import com.mobiledev.starterkit.data.local.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HabitTrackerViewModel @Inject constructor(
    val
    habitRepository: HabitRepository
) : ViewModel() {
    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits = _habits.asStateFlow()

    init {
        viewModelScope.launch {
            habitRepository.getHabits().collect { _habits.value = it }
        }
    }

    fun addHabit(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.addHabit(name)
        }
    }

    fun toggleHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.setHabitCompleted(habit.copy(isCompleted = habit.isCompleted.not()))
        }
    }
}
