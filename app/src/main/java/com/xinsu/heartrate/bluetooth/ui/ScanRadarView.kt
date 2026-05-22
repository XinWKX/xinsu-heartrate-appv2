package com.xinsu.heartrate.bluetooth.ui

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class ScanRadarView(
    context: Context
) : View(context) {

    private var scanning =
        false

    private var sweepAngle =
        0f

    private val gridPaint =
        Paint().apply {

            style = Paint.Style.STROKE

            strokeWidth = 2f

            color = Color.argb(
                80,
                0,
                255,
                255
            )

            isAntiAlias = true
        }

    private val sweepPaint =
        Paint().apply {

            style = Paint.Style.FILL

            shader = SweepGradient(

                0f,

                0f,

                intArrayOf(

                    Color.TRANSPARENT,

                    Color.CYAN
                ),

                null
            )

            isAntiAlias = true
        }

    fun startScan() {

        scanning = true

        invalidate()
    }

    fun stopScan() {

        scanning = false

        invalidate()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        val cx =
            width / 2f

        val cy =
            height / 2f

        val radius =
            width.coerceAtMost(
                height
            ) / 2f - 20f

        canvas.drawCircle(
            cx,
            cy,
            radius,
            gridPaint
        )

        canvas.drawCircle(
            cx,
            cy,
            radius * 0.7f,
            gridPaint
        )

        canvas.drawCircle(
            cx,
            cy,
            radius * 0.4f,
            gridPaint
        )

        repeat(4) {

            val angle =
                Math.toRadians(
                    (it * 90).toDouble()
                )

            val x =
                cx +
                cos(angle)
                    .toFloat() * radius

            val y =
                cy +
                sin(angle)
                    .toFloat() * radius

            canvas.drawLine(
                cx,
                cy,
                x,
                y,
                gridPaint
            )
        }

        if (scanning) {

            sweepAngle += 4f

            canvas.save()

            canvas.translate(
                cx,
                cy
            )

            canvas.rotate(
                sweepAngle
            )

            val rect = RectF(

                -radius,

                -radius,

                radius,

                radius
            )

            canvas.drawArc(

                rect,

                0f,

                45f,

                true,

                sweepPaint
            )

            canvas.restore()

            invalidate()
        }
    }
}
