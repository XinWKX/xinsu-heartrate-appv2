package com.xinsu.heartrate.particles.effects

import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.xinsu.heartrate.core.pulse.PulseEngine

class GlowLayer {

    private val glowPaint =
        Paint().apply {

            isAntiAlias = true

            maskFilter =
                BlurMaskFilter(
                    120f,
                    BlurMaskFilter.Blur.NORMAL
                )
        }

    /**
     * 绘制环境 Glow
     */
    fun render(

        canvas: Canvas,

        width: Int,

        height: Int
    ) {

        val pulse =
            PulseEngine.pulse

        val alpha =
            (20 + pulse * 25).toInt()

        glowPaint.color = Color.argb(

            alpha,

            0,

            255,

            140
        )

        val radius =
            width * (
                0.28f +
                pulse * 0.03f
            )

        canvas.drawCircle(

            width / 2f,

            height / 2f,

            radius,

            glowPaint
        )
    }
}
