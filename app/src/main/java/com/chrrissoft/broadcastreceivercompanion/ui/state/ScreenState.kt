package com.chrrissoft.broadcastreceivercompanion.ui.state

import com.chrrissoft.broadcastreceivercompanion.ui.state.CtxRegistration.Activity

data class ScreenState(
    val manifest: Boolean = false,
    val context: ContextRegistration = ContextRegistration(),
) {
    data class ContextRegistration(
        val registered: Boolean = false,
        val context: CtxRegistration = Activity,
        val permission: Permission = Permission.WithOut,
    )
}
