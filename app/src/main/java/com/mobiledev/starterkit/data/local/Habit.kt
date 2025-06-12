package com.mobiledev.starterkit.data.local

data class Habit(
     val id: Int,
     val name: String,
     val createdAt: Long,
     val isCompleted: Boolean = false,
)
