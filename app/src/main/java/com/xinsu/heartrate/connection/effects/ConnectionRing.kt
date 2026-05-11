package com.xinsu.heartrate.connection.effects

import android.graphics.*
import kotlin.math.sin

class ConnectionRing {

    private var time = 0f

    private val ringPaint =
        Paint().apply {

            style = Paint.Style.STROKE

            strokeWidth = 8f

            isAntiAlias = true
        }

    /**
     * 更新
     */
    fun update(
        deltaTime: Float
    ) {

        time += deltaTime * 2f
    }

    /**
     * 渲染
     */
    fun render(

        canvas: Canvas,

        width: Int,

        height: Int
    ) {

        val centerX =
            width / 2f

        val centerY =
            height / 2f

        val pulse =
            1f +
            sin(time * 3f) * 0.06f

        val radius =
            width.coerceAtMost(
                height
            ) * 0.16f * pulse

        val alpha =
            (
                120 +

                sin(time * 2f) * 40f
                ).toInt()

        ringPaint.shader =

            SweepGradient(

                centerX,
                centerY,

                intArrayOf(

                    Color.argb(

                        alpha,

                        255,

                        255,

                        255
                    ),

                    Color.argb(

                        30,

                        120,

                        255,

                        180
                    ),

                    Color.TRANSPARENT
                ),

                null
            )

        canvas.drawCircle(

            centerX,
            centerY,

            radius,

            ringPaint
        )
    }
}
