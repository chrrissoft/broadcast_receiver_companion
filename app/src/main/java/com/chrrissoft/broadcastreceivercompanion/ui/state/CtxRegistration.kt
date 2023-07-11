package com.chrrissoft.broadcastreceivercompanion.ui.state

import android.content.Context
import com.chrrissoft.broadcastreceivercompanion.MainActivity

enum class CtxRegistration {
    Activity, Application;

    fun resolve(activity: MainActivity, app: Context): Context {
        return when (this) {
            Activity -> activity
            Application -> app
        }
    }
}
