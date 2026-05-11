package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.xinsu.heartrate.particles.engine.ParticleEngine

class ParticleBackgroundView(
    context: Context
) : View(context) {

    private val particleEngine =
        ParticleEngine()

    private val paint =
        Paint().apply {

            isAntiAlias = true

            color = Color.WHITE
        }

    private var lastTime =
        System.currentTimeMillis()

    init {

        post {

            particleEngine.initialize(
                width,
                height
            )
        }
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        canvas.drawColor(
            Color.BLACK
        )

        val currentTime =
            System.currentTimeMillis()

        val deltaTime =
            (currentTime - lastTime)
                .toFloat() / 1000f

        lastTime = currentTime

        particleEngine.update(
            deltaTime,
            width,
            height
        )

        particleEngine
            .getParticles()
            .forEach { particle ->

                paint.alpha =
                    particle.alpha

                canvas.drawCircle(

                    particle.x,

                    particle.y,

                    particle.radius,

                    paint
                )
            }

        invalidate()
    }
}
