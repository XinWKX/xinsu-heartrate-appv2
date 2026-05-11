package com.xinsu.heartrate.transition.renderer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.xinsu.heartrate.transition.engine.TransitionEngine

class TransitionRenderer {

    private val blackoutPaint =
        Paint()

    /**
     * 绘制黑场
     */
    fun render(
        canvas: Canvas,

        width: Int,

        height: Int
    ) {

        val alpha =
            (
                TransitionEngine.progress *
                255
            ).toInt()

        blackoutPaint.color =
            Color.argb(

                alpha,

                0,
                0,
                0
            )

        canvas.drawRect(

            0f,
            0f,

            width.toFloat(),
            height.toFloat(),

            blackoutPaint
        )
    }
}
