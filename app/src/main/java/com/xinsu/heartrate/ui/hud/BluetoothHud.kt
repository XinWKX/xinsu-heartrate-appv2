package com.xinsu.heartrate.ui.hud

import android.content.Context
import android.graphics.*
import android.view.View
import com.xinsu.heartrate.bluetooth.core.BleState
import com.xinsu.heartrate.bluetooth.core.BleStateManager

class BluetoothHud(
    context: Context
) : View(context) {

    private val backgroundPaint =
        Paint().apply {

            color = Color.argb(
                70,
                20,
                20,
                20
            )

            isAntiAlias = true
        }

    private val borderPaint =
        Paint().apply {

            style = Paint.Style.STROKE

            strokeWidth = 3f

            color = Color.argb(
                120,
                255,
                255,
                255
            )

            isAntiAlias = true
        }

    private val textPaint =
        Paint().apply {

            color = Color.WHITE

            textSize = 34f

            isAntiAlias = true
        }

    private val statePaint =
        Paint().apply {

            textSize = 30f

            isAntiAlias = true
        }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        val rect = RectF(

            0f,

            0f,

            width.toFloat(),

            height.toFloat()
        )

        canvas.drawRoundRect(

            rect,

            28f,

            28f,

            backgroundPaint
        )

        canvas.drawRoundRect(

            rect,

            28f,

            28f,

            borderPaint
        )

        val state =
            BleStateManager.getState()

        val stateText = when (state) {

            BleState.IDLE ->
                "Bluetooth Idle"

            BleState.SCANNING ->
                "Scanning Devices"

            BleState.CONNECTING ->
                "Connecting"

            BleState.CONNECTED ->
                "Connected"

            BleState.DISCONNECTED ->
                "Disconnected"

            BleState.RECONNECTING ->
                "Reconnecting"
        }

        statePaint.color = when (state) {

            BleState.CONNECTED ->
                Color.GREEN

            BleState.SCANNING ->
                Color.CYAN

            BleState.CONNECTING ->
                Color.YELLOW

            BleState.RECONNECTING ->
                Color.MAGENTA

            BleState.DISCONNECTED ->
                Color.RED

            else ->
                Color.WHITE
        }

        canvas.drawText(

            "BLE STATUS",

            32f,

            42f,

            textPaint
        )

        canvas.drawText(

            stateText,

            32f,

            82f,

            statePaint
        )
    }
}
