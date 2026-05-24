package com.xinsu.heartrate.bluetooth.core

import android.os.Handler
import android.os.Looper

object BleReconnectEngine {

    private val handler =
        Handler(
            Looper.getMainLooper()
        )

    private var reconnectRunnable:
            Runnable? = null

    private var reconnectAction:
            (() -> Unit)? = null

    /**
     * 设置重连动作
     */
    fun setReconnectAction(

        action: () -> Unit
    ) {

        reconnectAction = action
    }

    /**
     * 尝试自动重连
     */
    fun attemptReconnect() {

        stopReconnect()

        reconnectRunnable = Runnable {

            reconnectAction?.invoke()
        }

        handler.postDelayed(

            reconnectRunnable!!,

            BleConstants
                .RECONNECT_DELAY
        )
    }

    /**
     * 停止自动重连
     */
    fun stopReconnect() {

        reconnectRunnable?.let {

            handler.removeCallbacks(it)
        }

        reconnectRunnable = null
    }
}
