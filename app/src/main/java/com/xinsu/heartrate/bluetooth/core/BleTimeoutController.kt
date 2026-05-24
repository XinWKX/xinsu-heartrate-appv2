package com.xinsu.heartrate.bluetooth.core

import android.os.Handler
import android.os.Looper

object BleTimeoutController {

    private val handler =
        Handler(
            Looper.getMainLooper()
        )

    private var timeoutRunnable:
            Runnable? = null

    /**
     * 启动连接超时
     */
    fun startTimeout(

        onTimeout:
        () -> Unit
    ) {

        cancel()

        timeoutRunnable = Runnable {

            BleStateManager.setState(
                BleState.FAILED
            )

            onTimeout.invoke()
        }

        handler.postDelayed(

            timeoutRunnable!!,

            BleConstants
                .CONNECTION_TIMEOUT
        )
    }

    /**
     * 取消超时
     */
    fun cancel() {

        timeoutRunnable?.let {

            handler.removeCallbacks(it)
        }

        timeoutRunnable = null
    }
}
