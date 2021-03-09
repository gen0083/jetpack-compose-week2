package com.example.androiddevchallenge

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val remain: MutableStateFlow<Long> = MutableStateFlow(DEFAULT_TIME)
    val displayString: Flow<String> = remain.mapLatest { toDisplayString(it) }
    val remainRatio = remain.mapLatest { it.toFloat() / DEFAULT_TIME }
    var timerState = mutableStateOf(TimerState.STOP)
    private var timerJob: Job? = null

    fun onStart() {
        if (timerState.value == TimerState.START) return
        timerState.value = TimerState.START
        timerJob = viewModelScope.launch {
            while (remain.value > 0L) {
                delay(1000)
                ensureActive()
                remain.value--
            }
            timerState.value = TimerState.STOP
            remain.value = DEFAULT_TIME
        }
    }

    fun onStop() {
        timerJob?.cancel()
        timerState.value = TimerState.STOP
        remain.value = DEFAULT_TIME
    }

    private fun toDisplayString(time: Long): String {
        val minute = time / 60
        val second = time % 60
        return "%02d:%02d".format(minute, second)
    }

    companion object {
        const val DEFAULT_TIME = 60L
    }
}

enum class TimerState {
    START, STOP, PAUSE
}
