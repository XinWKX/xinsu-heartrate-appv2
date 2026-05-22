package com.xinsu.heartrate.ui.hud

import android.content.Context
import android.graphics.*
import android.view.View
import com.xinsu.heartrate.bluetooth.core.BleState
import com.xinsu.heartrate.bluetooth.core.BleStateManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TopStatusBar(
    context: Context
) : View(context) {

    private val textPaint = Paint().apply {

        color = Color.WHITE

        textSize = 34f

        isAntiAlias = true
    }

    private val statePaint = Paint().apply {

        textSize = 30f

        isAntiAlias = true
    }

    private val dateFormat =

        SimpleDateFormat(

            "HH:mm:ss",

            Locale.getDefault()
        )

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        val state =
            BleStateManager.getState()

        val stateText =

            when (state) {

                BleState.IDLE ->
                    "IDLE"

                BleState.SCANNING ->
                    "SCANNING"

                BleState.CONNECTING ->
                    "CONNECTING"

                BleState.CONNECTED ->
                    "CONNECTED"

                BleState.DISCONNECTED ->
                    "DISCONNECTED"

                BleState.RECONNECTING ->
                    "RECONNECTING"
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

            "XINSU HEART RATE",

            0f,

            42f,

            textPaint
        )

        canvas.drawText(

            stateText,

            width - 320f,

            42f,

            statePaint
        )

        canvas.drawText(

            dateFormat.format(
                Date()
            ),

            width - 180f,

            85f,

            textPaint
        )

        invalidate()
    }
}
