package com.xinsu.heartrate.bluetooth.ui

import android.content.Context
import android.graphics.*
import android.view.View
import com.xinsu.heartrate.core.render.RenderListener
import com.xinsu.heartrate.core.render.RenderLoop
import kotlin.math.cos
import kotlin.math.sin

class ScanRadarView(

    context: Context

) : View(context),
    RenderListener {

    private var rotation = 0f

    private val ringPaint =
        Paint().apply {

            style = Paint.Style.STROKE

            strokeWidth = 2f

            isAntiAlias = true

            color =
                Color.argb(

                    40,

                    255,

                    255,

                    255
                )
        }

    private val sweepPaint =
        Paint().apply {

            isAntiAlias = true
        }

    init {

        RenderLoop.addListener(this)
    }

    override fun onDetachedFromWindow() {

        super.onDetachedFromWindow()

        RenderLoop.removeListener(this)
    }

    override fun onRender(
        deltaTime: Float
    ) {

        rotation +=
            deltaTime * 45f

        postInvalidateOnAnimation()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        val centerX =
            width / 2f

        val centerY =
            height / 2f

        val radius =
            width.coerceAtMost(
                height
            ) * 0.35f

        // Rings
        repeat(4) {

            canvas.drawCircle(

                centerX,
                centerY,

                radius *
                        ((it + 1) / 4f),

                ringPaint
            )
        }

        // Sweep
        val sweepAngle =
            Math.toRadians(
                rotation.toDouble()
            )

        val endX =
            centerX +
            cos(sweepAngle)
                .toFloat() * radius

        val endY =
            centerY +
            sin(sweepAngle)
                .toFloat() * radius

        sweepPaint.shader =

            LinearGradient(

                centerX,
                centerY,

                endX,
                endY,

                Color.argb(

                    180,

                    120,

                    255,

                    180
                ),

                Color.TRANSPARENT,

                Shader.TileMode.CLAMP
            )

        sweepPaint.strokeWidth =
            8f

        canvas.drawLine(

            centerX,
            centerY,

            endX,
            endY,

            sweepPaint
        )
    }
    }
