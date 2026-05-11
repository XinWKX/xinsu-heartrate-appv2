package com.xinsu.heartrate.bluetooth.core

object BleReconnectEngine {

    private var reconnectAction:
            (() -> Unit)? = null

    fun setReconnectAction(
        action: () -> Unit
    ) {

        reconnectAction = action
    }

    fun attemptReconnect() {

        reconnectAction?.invoke()
    }

    fun startReconnect() {

        attemptReconnect()
    }

    fun stopReconnect() {

        reconnectAction = null
    }
}
