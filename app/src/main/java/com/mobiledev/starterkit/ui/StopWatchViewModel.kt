package com.mobiledev.starterkit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class StopWatchViewModel @Inject constructor() : ViewModel() {
    private val _timer = MutableStateFlow(0)
    val timer = _timer.asStateFlow()
    var timerJob: Job? = null

    private val _laps = MutableStateFlow(emptyList<String>())
    val laps = _laps.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    fun toggleTimer() {
        if (isRunning.value) {
            pause()
        } else {
            start()
        }
    }

    private fun start() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                _isRunning.value = true
                delay(8)
                _timer.update { it + 1 }
            }
        }
    }

    private fun pause() {
        timerJob?.cancel()
        _isRunning.value = false
    }

    fun reset() {
        _timer.value = 0
        timerJob?.cancel()
        _laps.value = emptyList()
        _isRunning.value = false
    }

    fun lap() {
        _laps.value = _laps.value + timer.value.toString()
    }
}
