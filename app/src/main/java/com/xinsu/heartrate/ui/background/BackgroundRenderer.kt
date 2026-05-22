package com.xinsu.heartrate.ui.background

import android.graphics.*
import com.xinsu.heartrate.settings.core.SettingsManager

object BackgroundRenderer {

    private val gridPaint =
        Paint().apply {

            color = Color.argb(
                35,
                255,
                255,
                255
            )

            strokeWidth = 1f

            isAntiAlias = true
        }

    private val gradientPaint =
        Paint().apply {

            isAntiAlias = true
        }

    fun render(

        canvas: Canvas,

        width: Int,

        height: Int
    ) {

        when (

            SettingsManager.backgroundMode

        ) {

            SettingsManager
                .BackgroundMode.BLACK -> {

                canvas.drawColor(
                    Color.BLACK
                )
            }

            SettingsManager
                .BackgroundMode.GRID -> {

                drawGrid(
                    canvas,
                    width,
                    height
                )
            }

            SettingsManager
                .BackgroundMode.PARTICLE -> {

                canvas.drawColor(
                    Color.BLACK
                )
            }

            SettingsManager
                .BackgroundMode.GRADIENT -> {

                drawGradient(
                    canvas,
                    width,
                    height
                )
            }
        }
    }

    /**
     * 网格背景
     */
    private fun drawGrid(

        canvas: Canvas,

        width: Int,

        height: Int
    ) {

        canvas.drawColor(
            Color.BLACK
        )

        val spacing = 80

        var x = 0

        while (x < width) {

            canvas.drawLine(

                x.toFloat(),

                0f,

                x.toFloat(),

                height.toFloat(),

                gridPaint
            )

            x += spacing
        }

        var y = 0

        while (y < height) {

            canvas.drawLine(

                0f,

                y.toFloat(),

                width.toFloat(),

                y.toFloat(),

                gridPaint
            )

            y += spacing
        }
    }

    /**
     * 渐变背景
     */
    private fun drawGradient(

        canvas: Canvas,

        width: Int,

        height: Int
    ) {

        val shader = LinearGradient(

            0f,

            0f,

            width.toFloat(),

            height.toFloat(),

            intArrayOf(

                Color.rgb(
                    0,
                    20,
                    30
                ),

                Color.BLACK,

                Color.rgb(
                    0,
                    40,
                    50
                )
            ),

            null,

            Shader.TileMode.CLAMP
        )

        gradientPaint.shader =
            shader

        canvas.drawRect(

            0f,

            0f,

            width.toFloat(),

            height.toFloat(),

            gradientPaint
        )
    }
}
