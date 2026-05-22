package com.xinsu.heartrate.bluetooth.ui

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class ScanRadarView(
    context: Context
) : View(context) {

    private var isScanning =
        false

    private var sweepAngle =
        0f

    private val gridPaint = Paint().apply {

        style = Paint.Style.STROKE

        color = Color.argb(
            50,
            255,
            255,
            255
        )

        strokeWidth = 2f

        isAntiAlias = true
    }

    private val sweepPaint = Paint().apply {

        style = Paint.Style.FILL

        isAntiAlias = true
    }

    private val centerPaint = Paint().apply {

        color = Color.WHITE

        isAntiAlias = true
    }

    fun startScan() {

        isScanning = true

        invalidate()
    }

    fun stopScan() {

        isScanning = false

        invalidate()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        val cx = width / 2f

        val cy = height / 2f

        val radius =
            width.coerceAtMost(
                height
            ) * 0.42f

        // Radar Rings
        repeat(4) {

            index ->

            canvas.drawCircle(

                cx,

                cy,

                radius *
                        ((index + 1) / 4f),

                gridPaint
            )
        }

        // Cross
        canvas.drawLine(

            cx - radius,

            cy,

            cx + radius,

            cy,

            gridPaint
        )

        canvas.drawLine(

            cx,

            cy - radius,

            cx,

            cy + radius,

            gridPaint
        )

        // Sweep
        if (isScanning) {

            sweepAngle += 3.5f

            val gradient = SweepGradient(

                cx,

                cy,

                intArrayOf(

                    Color.TRANSPARENT,

                    Color.argb(
                        40,
                        0,
                        255,
                        255
                    ),

                    Color.argb(
                        180,
                        0,
                        255,
                        255
                    )
                ),

                floatArrayOf(

                    0f,

                    0.85f,

                    1f
                )
            )

            val matrix = Matrix()

            matrix.postRotate(

                sweepAngle,

                cx,

                cy
            )

            gradient.setLocalMatrix(
                matrix
            )

            sweepPaint.shader =
                gradient

            canvas.drawCircle(

                cx,

                cy,

                radius,

                sweepPaint
            )

            // Radar Dot
            val radians =

                Math.toRadians(
                    sweepAngle.toDouble()
                )

            val dotX =

                cx +

                cos(radians)
                    .toFloat() * radius

            val dotY =

                cy +

                sin(radians)
                    .toFloat() * radius

            canvas.drawCircle(

                dotX,

                dotY,

                10f,

                centerPaint
            )

            invalidate()
        }

        // Center Point
        canvas.drawCircle(

            cx,

            cy,

            12f,

            centerPaint
        )
    }
}
