package com.xinsu.heartrate.bluetooth.core

object BleStateManager {

    private var currentState:
            BleState = BleState.IDLE

    fun setState(
        state: BleState
    ) {

        currentState = state
    }

    fun getState():
            BleState {

        return currentState
    }

    fun isConnected():
            Boolean {

        return currentState ==
                BleState.CONNECTED
    }

    fun isScanning():
            Boolean {

        return currentState ==
                BleState.SCANNING
    }

    fun isConnecting():
            Boolean {

        return currentState ==
                BleState.CONNECTING
    }
}
