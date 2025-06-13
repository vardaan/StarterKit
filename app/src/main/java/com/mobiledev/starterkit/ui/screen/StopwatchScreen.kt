package com.mobiledev.starterkit.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobiledev.starterkit.ui.StopWatchViewModel
import com.mobiledev.starterkit.ui.screen.Utils.formatElapsed

@Composable
fun StopwatchScreen(modifier: Modifier, stopwatchViewModel: StopWatchViewModel) {
    val timerValue = stopwatchViewModel.timer.collectAsState()
    val laps = stopwatchViewModel.laps.collectAsState()
    val isRunning = stopwatchViewModel.isRunning.collectAsState()
    val formattedTimerValue = formatElapsed(timerValue.value.toLong())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "StopWatch Application", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.size(16.dp))
        Text(text = formattedTimerValue, style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.size(16.dp))
        Row {
            Button(onClick = {
                stopwatchViewModel.toggleTimer()
            }) {
                Text(text = if (isRunning.value) "Stop" else "Start")
            }
            Spacer(Modifier.size(16.dp))
            if (isRunning.value)
                Button(onClick = {
                    stopwatchViewModel.lap()
                }) {
                    Text(text = "Lap")
                }
            Spacer(Modifier.size(16.dp))

            if (!isRunning.value)
                Button(onClick = {
                    stopwatchViewModel.reset()
                }) {
                    Text(text = "Reset")
                }
        }

        Text(text = "Laps", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(laps.value.size) {
                Surface(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Lap ${it + 1}: " + laps.value[it],
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}
