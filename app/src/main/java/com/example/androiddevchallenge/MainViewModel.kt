package com.example.androiddevchallenge

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val _remain: MutableStateFlow<Long> = MutableStateFlow(DEFAULT_TIME)
    val remain: MutableStateFlow<Long> = _remain
    var timerState = mutableStateOf(TimerState.STOP)
    
    fun onStart() {
        timerState.value = TimerState.START
        viewModelScope.launch {
            while (_remain.value > 0L) {
                delay(1000)
                _remain.value--
            }
            timerState.value = TimerState.STOP
            _remain.value = DEFAULT_TIME
        }
    }
    
    fun onStop() {
        timerState.value = TimerState.STOP
        _remain.value = DEFAULT_TIME
        viewModelScope.cancel()
    }
    
    companion object {
        const val DEFAULT_TIME = 180L
    }
}

enum class TimerState {
    START, STOP, PAUSE
}
