package com.example.androiddevchallenge.ui.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.MainViewModel
import com.example.androiddevchallenge.TimerState

@Composable
fun TimerScreen(vm: MainViewModel) {
    val displayText: String by vm.displayString.collectAsState(initial = "")
    val remainRatio: Float by vm.remainRatio.collectAsState(initial = 1f)
    Column(Modifier.fillMaxWidth()) {
        Timer(
                displayText = displayText,
                remainRatio = remainRatio,
        )
        val modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 32.dp, vertical = 18.dp)
        when (vm.timerState.value) {
            TimerState.START -> Button(onClick = vm::onStop, modifier = modifier) {
                Text(text = "STOP")
            }
            TimerState.STOP -> Button(onClick = vm::onStart, modifier = modifier) {
                Text(text = "START")
            }
            else -> TODO("${vm.timerState.value} is not implemented")
        }
    }
}

@Composable
fun Timer(
        modifier: Modifier = Modifier,
        displayText: String,
        remainRatio: Float = 1f,
) {
    Column(
            modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text(text = displayText)
        Text(text = "remain ratio = $remainRatio")
        CircularProgressIndicator(progress = remainRatio)
    }
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