package com.xinsu.heartrate.ecg.renderer

import android.graphics.*
import com.xinsu.heartrate.ecg.engine.EcgEngine

class EcgRenderer {

    private val glowPaint =
        Paint().apply {

            color = Color.argb(

                120,

                0,

                255,

                140
            )

            strokeWidth = 12f

            style = Paint.Style.STROKE

            isAntiAlias = true

            strokeCap =
                Paint.Cap.ROUND

            maskFilter =
                BlurMaskFilter(

                    20f,

                    BlurMaskFilter.Blur.NORMAL
                )
        }

    private val linePaint =
        Paint().apply {

            color = Color.argb(

                255,

                120,

                255,

                180
            )

            strokeWidth = 4f

            style = Paint.Style.STROKE

            isAntiAlias = true

            strokeCap =
                Paint.Cap.ROUND
        }

    /**
     * 绘制 ECG
     */
    fun render(

        canvas: Canvas,

        engine: EcgEngine,

        centerY: Float
    ) {

        val path = Path()

        val points =
            engine.getPoints()

        if (points.isEmpty()) {

            return
        }

        path.moveTo(

            points.first().x,

            centerY -
                    points.first().y
        )

        points.forEach {

            path.lineTo(

                it.x,

                centerY - it.y
            )
        }

        // Glow
        canvas.drawPath(
            path,
            glowPaint
        )

        // 主线
        canvas.drawPath(
            path,
            linePaint
        )
    }
}
