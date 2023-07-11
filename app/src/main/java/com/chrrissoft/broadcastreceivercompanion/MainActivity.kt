package com.chrrissoft.broadcastreceivercompanion

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.pm.PackageManager.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.chrrissoft.broadcastreceivercompanion.BroadcastReceiverCompanion.Companion.filter
import com.chrrissoft.broadcastreceivercompanion.BroadcastReceiverCompanion.ManifestBroadcastReceiverCompanion
import com.chrrissoft.broadcastreceivercompanion.ui.components.AppContainer
import com.chrrissoft.broadcastreceivercompanion.ui.components.Screen
import com.chrrissoft.broadcastreceivercompanion.ui.state.CtxRegistration
import com.chrrissoft.broadcastreceivercompanion.ui.state.CtxRegistration.Activity
import com.chrrissoft.broadcastreceivercompanion.ui.state.Permission
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()

        setContent {
            val state by app.stateFlow.collectAsState()
            AppContainer {
                Screen(state = state) { app.updateState(it) }
                setBarsColors()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (app.state.context.registered && app.state.context.context==Activity) {
            val context = app.state.context.copy(registered = false)
            app.updateState(app.state.copy(context = context))
        }
    }

    private fun observe() {
        app.stateFlow
            .onEach {
                if (it.context.registered) {
                    register(it.context.context, app.custom, it.context.permission)
                } else unregister(app.custom)
            }
            .onEach {
                if (it.manifest) enableReceiver(ManifestBroadcastReceiverCompanion::class.java)
                else disableReceiver(ManifestBroadcastReceiverCompanion::class.java)
            }
            .launchIn(lifecycleScope)
    }

    private fun register(
        ctx: CtxRegistration,
        receiver: BroadcastReceiver,
        permission: Permission
    ) {
        val context = ctx.resolve(this, this.applicationContext)
        unregister(receiver)
        context.registerReceiver(receiver, filter, permission.manifest, null)
    }

    private fun unregister(receiver: BroadcastReceiver) {
        try {
            unregisterReceiver(receiver)
        } catch (_: Exception) {
        }
        try {
            this.applicationContext.unregisterReceiver(receiver)
        } catch (_: Exception) {
        }
    }

    private fun <R : BroadcastReceiver> disableReceiver(receiver: Class<R>) {
        val component = ComponentName(this, receiver)
        packageManager.setComponentEnabledSetting(
            component, COMPONENT_ENABLED_STATE_DISABLED, DONT_KILL_APP
        )
    }

    private fun <R : BroadcastReceiver> enableReceiver(receiver: Class<R>) {
        val component = ComponentName(this, receiver)
        packageManager.setComponentEnabledSetting(
            component, COMPONENT_ENABLED_STATE_ENABLED, DONT_KILL_APP
        )
    }

    companion object {
        @SuppressLint("ComposableNaming")
        @Composable
        fun setBarsColors(
            bottom: Color = colorScheme.onTertiary,
            status: Color = colorScheme.tertiaryContainer,
        ) {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            DisposableEffect(systemUiController, useDarkIcons) {
                systemUiController.setStatusBarColor(status, useDarkIcons)
                systemUiController.setNavigationBarColor(bottom)
                onDispose {}
            }
        }
    }
}
