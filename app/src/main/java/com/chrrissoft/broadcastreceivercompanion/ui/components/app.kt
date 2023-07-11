package com.chrrissoft.broadcastreceivercompanion.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.broadcastreceivercompanion.ui.theme.BroadcastReceiverCompanionTheme

@Composable
fun AppContainer(app: @Composable () -> Unit) {
    BroadcastReceiverCompanionTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) { app() }
    }
}