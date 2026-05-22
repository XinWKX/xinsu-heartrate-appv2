package com.xinsu.heartrate.connection.effects

import android.content.Context
import android.graphics.*
import android.view.View

class ConnectionAnimationView(
    context: Context
) : View(context) {

    private var state =
        "IDLE"

    private val textPaint =
        Paint().apply {

            color = Color.WHITE

            textSize = 58f

            textAlign = Paint.Align.CENTER

            isAntiAlias = true
        }

    fun showConnecting() {

        state = "CONNECTING"

        invalidate()
    }

    fun showConnected() {

        state = "CONNECTED"

        invalidate()
    }

    fun showDisconnected() {

        state = "DISCONNECTED"

        invalidate()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        if (state == "IDLE") {

            return
        }

        textPaint.color = when (state) {

            "CONNECTED" ->
                Color.GREEN

            "CONNECTING" ->
                Color.YELLOW

            else ->
                Color.RED
        }

        canvas.drawText(

            state,

            width / 2f,

            height / 2f + 320f,

            textPaint
        )
    }
}
