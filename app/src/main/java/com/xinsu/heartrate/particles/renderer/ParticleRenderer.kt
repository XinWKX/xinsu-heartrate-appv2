package com.xinsu.heartrate.particles.renderer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.xinsu.heartrate.particles.engine.ParticleEngine

class ParticleRenderer {

    private val paint = Paint().apply {

        isAntiAlias = true
    }

    /**
     * 绘制粒子
     */
    fun render(

        canvas: Canvas,

        engine: ParticleEngine
    ) {

        engine.getParticles().forEach {

            paint.color = Color.argb(

                it.alpha,

                0,

                255,

                120
            )

            canvas.drawCircle(

                it.x,

                it.y,

                it.radius,

                paint
            )
        }
    }
}
