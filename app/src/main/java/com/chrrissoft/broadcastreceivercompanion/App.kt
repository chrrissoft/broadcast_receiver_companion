package com.chrrissoft.broadcastreceivercompanion

import android.app.Application
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.pm.PackageManager
import com.chrrissoft.broadcastreceivercompanion.BroadcastReceiverCompanion.ContextBroadcastReceiverCompanion
import com.chrrissoft.broadcastreceivercompanion.ui.state.ScreenState
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    private val _state = MutableStateFlow(ScreenState())
    val stateFlow = _state.asStateFlow()
    val state get() = stateFlow.value

    @Inject
    lateinit var custom: ContextBroadcastReceiverCompanion

    fun updateState(state: ScreenState) {
        _state.update { state }
    }

    override fun onCreate() {
        super.onCreate()
        getEnabledState(
            onEnabled = {
                updateState(state.copy(manifest = true))
            },
            onDisabled = {
                updateState(state.copy(manifest = false))
            },
            receiver = BroadcastReceiverCompanion.ManifestBroadcastReceiverCompanion::class.java,
        )
    }

    private fun <R : BroadcastReceiver> getEnabledState(
        receiver: Class<R>,
        onEnabled: () -> Unit,
        onDisabled: () -> Unit,
    ) {
        val component = ComponentName(this, receiver)
        when (packageManager.getComponentEnabledSetting(component)) {
            PackageManager.COMPONENT_ENABLED_STATE_DEFAULT -> {
                onDisabled()
            }
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED -> {
                onDisabled()
            }
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED -> {}
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER -> {}
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED -> {
                onEnabled()
            }
        }
    }

}
