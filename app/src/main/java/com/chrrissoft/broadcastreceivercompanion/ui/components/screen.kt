package com.chrrissoft.broadcastreceivercompanion.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrrissoft.broadcastreceivercompanion.ui.state.ScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    state: ScreenState,
    onStateChanged: (ScreenState) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = centerAlignedTopAppBarColors(
                    containerColor = colorScheme.tertiaryContainer,
                    titleContentColor = colorScheme.tertiary
                ),
                title = { Text(text = "Broadcasts Receiver Companion") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(colorScheme.onTertiary)
            .padding(10.dp)) {
            Container(title = "Manifest Enabled") {
                Checkbox(
                    text = "Manifest receiver",
                    enabled = true,
                    checked = state.manifest,
                    onCheckedChanged = {
                        onStateChanged(state.copy(manifest = it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
            }

            Divider(color = colorScheme.tertiary, modifier = Modifier.padding(10.dp))

            Container(title = "Context registration") {
                ContextRegistration(
                    title = "Context registration",
                    state = state.context,
                    onStateChanged = {
                        onStateChanged(state.copy(context = it))
                    }
                )
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}
