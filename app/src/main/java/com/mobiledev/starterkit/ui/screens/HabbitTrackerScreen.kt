package com.mobiledev.starterkit.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mobiledev.starterkit.ui.HabitTrackerViewModel

@Composable
fun HabitTrackerScreen(modifier: Modifier = Modifier, habitViewModel: HabitTrackerViewModel) {
    val name = remember { mutableStateOf("") }
    val habits = habitViewModel.habits.collectAsStateWithLifecycle()
    Column(modifier = modifier) {
        Text("Habbit Tracker Screen", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(16.dp))
        Row {
            TextField(value = name.value, onValueChange = { name.value = it })
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                onClick = {
                    habitViewModel.addHabit(name.value)
                    name.value = ""
                },
            ) {
                Text("Add Habbit")
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        LazyColumn(modifier = Modifier) {
            items(habits.value.size, key = { habits.value[it].id }) { index ->
                val habit = habits.value[index]
                Surface(
                    modifier = Modifier
                        .clickable {
                            habitViewModel.toggleHabit(habit)
                        }
                        .fillParentMaxWidth()
                        .padding(horizontal = 16.dp)) {
                    Text(
                        text = habit.name,
                        color = if (habit.isCompleted) Color.Green else Color.Black,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
