package com.xinsu.heartrate.connection.effects

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.sin

class ConnectionAnimationView(
    context: Context
) : View(context) {

    private var mode =
        Mode.HIDDEN

    private var animTime = 0f

    private enum class Mode {

        HIDDEN,

        SCANNING,

        CONNECTING,

        CONNECTED,

        DISCONNECTED,

        RECONNECTING
    }

    private val paint = Paint().apply {

        style = Paint.Style.STROKE

        strokeWidth = 12f

        isAntiAlias = true
    }

    fun showScanning() {

        mode = Mode.SCANNING

        invalidate()
    }

    fun showConnecting() {

        mode = Mode.CONNECTING

        invalidate()
    }

    fun showConnected() {

        mode = Mode.CONNECTED

        invalidate()
    }

    fun showDisconnected() {

        mode = Mode.DISCONNECTED

        invalidate()
    }

    fun showReconnecting() {

        mode = Mode.RECONNECTING

        invalidate()
    }

    fun hide() {

        mode = Mode.HIDDEN

        invalidate()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        if (
            mode == Mode.HIDDEN
        ) {

            return
        }

        animTime += 0.05f

        val cx = width / 2f

        val cy = height / 2f

        val pulse = (

            sin(animTime) * 0.5f
                    + 0.5f

        ).toFloat()

        val radius =

            180f +

            pulse * 40f

        paint.color = when (mode) {

            Mode.SCANNING ->
                Color.CYAN

            Mode.CONNECTING ->
                Color.YELLOW

            Mode.CONNECTED ->
                Color.GREEN

            Mode.DISCONNECTED ->
                Color.RED

            Mode.RECONNECTING ->
                Color.MAGENTA

            else ->
                Color.TRANSPARENT
        }

        canvas.drawCircle(

            cx,

            cy,

            radius,

            paint
        )

        invalidate()
    }
}
