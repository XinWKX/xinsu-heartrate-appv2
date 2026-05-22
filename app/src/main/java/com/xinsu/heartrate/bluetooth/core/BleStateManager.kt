package com.xinsu.heartrate.bluetooth.core

object BleStateManager {

    private var currentState =
        BleState.IDLE

    private var listener:
            ((BleState) -> Unit)?
        = null

    fun getState():
            BleState {

        return currentState
    }

    fun setState(
        state: BleState
    ) {

        currentState = state

        listener?.invoke(state)
    }

    fun setListener(
        callback:
        (BleState) -> Unit
    ) {

        listener = callback
    }
}
