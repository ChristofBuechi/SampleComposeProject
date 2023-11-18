package com.medium

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 1200.dp, height = 800.dp),
        title = "FollowingArrows"
    ) {
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            FollowingArrows()
        }
    }
}
