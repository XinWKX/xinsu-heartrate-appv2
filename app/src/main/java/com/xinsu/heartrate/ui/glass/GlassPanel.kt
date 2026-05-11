package com.xinsu.heartrate.ui.glass

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class GlassPanel @JvmOverloads constructor(

    context: Context,

    attrs: AttributeSet? = null

) : View(context, attrs) {

    private val backgroundPaint =
        Paint().apply {

            isAntiAlias = true
        }

    private val borderPaint =
        Paint().apply {

            isAntiAlias = true

            style = Paint.Style.STROKE

            strokeWidth = 1.5f
        }

    private val glowPaint =
        Paint().apply {

            isAntiAlias = true

            maskFilter =
                BlurMaskFilter(

                    40f,

                    BlurMaskFilter.Blur.NORMAL
                )
        }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        renderGlow(canvas)

        renderGlass(canvas)

        renderBorder(canvas)
    }

    /**
     * 柔光
     */
    private fun renderGlow(
        canvas: Canvas
    ) {

        glowPaint.color =
            Color.argb(

                18,

                255,

                255,

                255
            )

        canvas.drawRoundRect(

            0f,
            0f,

            width.toFloat(),
            height.toFloat(),

            48f,
            48f,

            glowPaint
        )
    }

    /**
     * 玻璃主体
     */
    private fun renderGlass(
        canvas: Canvas
    ) {

        val shader =
            LinearGradient(

                0f,
                0f,

                width.toFloat(),
                height.toFloat(),

                intArrayOf(

                    Color.argb(
                        22,
                        255,
                        255,
                        255
                    ),

                    Color.argb(
                        10,
                        255,
                        255,
                        255
                    )
                ),

                null,

                Shader.TileMode.CLAMP
            )

        backgroundPaint.shader =
            shader

        canvas.drawRoundRect(

            0f,
            0f,

            width.toFloat(),
            height.toFloat(),

            48f,
            48f,

            backgroundPaint
        )
    }

    /**
     * 边缘高光
     */
    private fun renderBorder(
        canvas: Canvas
    ) {

        borderPaint.color =
            Color.argb(

                30,

                255,

                255,

                255
            )

        canvas.drawRoundRect(

            1f,
            1f,

            width - 1f,
            height - 1f,

            48f,
            48f,

            borderPaint
        )
    }
}
