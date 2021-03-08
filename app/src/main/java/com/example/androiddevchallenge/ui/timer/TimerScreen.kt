package com.example.androiddevchallenge.ui.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.MainViewModel
import com.example.androiddevchallenge.TimerState

@Composable
fun TimerScreen(vm: MainViewModel) {
    val displayText: String by vm.displayString.collectAsState(initial = "")
    val remainRatio: Float by vm.remainRatio.collectAsState(initial = 1f)
    Column() {
        Timer(displayText, remainRatio)
        when (vm.timerState.value) {
            TimerState.START -> Button(onClick = vm::onStop) {
                Text(text = "STOP")
            }
            TimerState.STOP -> Button(onClick = vm::onStart) {
                Text(text = "START")
            }
            else -> TODO("${vm.timerState.value} is not implemented")
        }
    }
}

@Composable
fun Timer(displayText: String, remainRatio: Float = 1f) {
    Text(text = displayText)
    Text(text = "remain ratio = $remainRatio")
}

@Composable
@Preview("Default timer screen")
fun TimerScreenPreview() {
    val vm = MainViewModel()
    TimerScreen(vm = vm)
}

@Composable
@Preview()
fun TimerScreenPreviewStateStart() {
    val vm = MainViewModel()
    vm.onStart()
    TimerScreen(vm = vm)
}