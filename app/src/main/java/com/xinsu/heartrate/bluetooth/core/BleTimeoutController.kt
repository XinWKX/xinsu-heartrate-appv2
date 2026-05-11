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
     * 开始连接超时
     */
    fun startTimeout(

        timeout: Long = 10000,

        onTimeout: () -> Unit
    ) {

        cancel()

        timeoutRunnable =
            Runnable {

                BleStateManager.setState(
                    BleState.FAILED
                )

                BleConnectionLock.unlock()

                onTimeout()
            }

        handler.postDelayed(

            timeoutRunnable!!,

            timeout
        )
    }

    /**
     * 取消超时
     */
    fun cancel() {

        timeoutRunnable?.let {

            handler.removeCallbacks(it)
        }
    }
}
