package com.xinsu.heartrate.ui.hud

import android.content.Context
import android.graphics.*
import android.view.View
import com.xinsu.heartrate.bluetooth.data.HeartRateRepository

class BluetoothHud(
    context: Context
) : View(context) {

    private val textPaint = Paint().apply {

        color = Color.WHITE

        textSize = 38f

        isAntiAlias = true
    }

    private val statusPaint = Paint().apply {

        textSize = 32f

        isAntiAlias = true
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        val connected =
            HeartRateRepository
                .isConnected

        val deviceName =
            HeartRateRepository
                .connectedDeviceName

        statusPaint.color = if (
            connected
        ) {

            Color.GREEN

        } else {

            Color.RED
        }

        canvas.drawText(

            deviceName,

            20f,

            50f,

            textPaint
        )

        canvas.drawText(

            if (connected)
                "CONNECTED"
            else
                "DISCONNECTED",

            20f,

            95f,

            statusPaint
        )

        invalidate()
    }
}
