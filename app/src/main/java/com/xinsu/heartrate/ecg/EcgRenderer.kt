package com.xinsu.heartrate.ecg

import android.graphics.*
import kotlin.math.max

class EcgRenderer {

    private val glowPaint =
        Paint().apply {

            color = Color.WHITE

            strokeWidth = 10f

            style = Paint.Style.STROKE

            isAntiAlias = true

            maskFilter =
                BlurMaskFilter(

                    18f,

                    BlurMaskFilter.Blur.NORMAL
                )
        }

    private val linePaint =
        Paint().apply {

            color = Color.WHITE

            strokeWidth = 4f

            style = Paint.Style.STROKE

            isAntiAlias = true
        }

    /**
     * 渲染
     */
    fun render(

        canvas: Canvas,

        width: Int,

        height: Int
    ) {

        val points =
            EcgDataBuffer.get()

        if (points.size < 2) {

            return
        }

        val path = Path()

        val centerY =
            height / 2f

        val spacing =
            width / 240f

        points.forEachIndexed {

                index,
                point ->

            val x =
                index * spacing

            val y =
                centerY -
                point.y * 220f

            if (index == 0) {

                path.moveTo(x, y)

            } else {

                path.lineTo(x, y)
            }
        }

        canvas.drawPath(
            path,
            glowPaint
        )

        canvas.drawPath(
            path,
            linePaint
        )
    }
}
