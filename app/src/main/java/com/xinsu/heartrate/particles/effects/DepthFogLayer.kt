package com.xinsu.heartrate.particles.effects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader

class DepthFogLayer {

    private val paint =
        Paint()

    /**
     * 绘制空间雾层
     */
    fun render(

        canvas: Canvas,

        width: Int,

        height: Int
    ) {

        val shader =
            LinearGradient(

                0f,
                0f,

                0f,
                height.toFloat(),

                intArrayOf(

                    Color.argb(
                        10,
                        0,
                        255,
                        120
                    ),

                    Color.TRANSPARENT,

                    Color.argb(
                        25,
                        0,
                        255,
                        120
                    )
                ),

                null,

                Shader.TileMode.CLAMP
            )

        paint.shader = shader

        canvas.drawRect(

            0f,
            0f,

            width.toFloat(),
            height.toFloat(),

            paint
        )
    }
}
