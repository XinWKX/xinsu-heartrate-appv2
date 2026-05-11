package com.xinsu.heartrate.connection.effects

import android.graphics.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class ConnectionParticles {

    private data class Particle(

        var angle: Float,

        var distance: Float,

        var speed: Float
    )

    private val particles =
        mutableListOf<Particle>()

    private val paint =
        Paint().apply {

            isAntiAlias = true
        }

    init {

        repeat(60) {

            particles.add(

                Particle(

                    angle =
                        Random.nextFloat()
                                * 360f,

                    distance =
                        260f +
                        Random.nextFloat()
                                * 180f,

                    speed =
                        0.4f +
                        Random.nextFloat()
                )
            )
        }
    }

    /**
     * 更新
     */
    fun update(
        deltaTime: Float
    ) {

        particles.forEach {

            it.distance -=
                it.speed *
                deltaTime *
                60f

            if (it.distance < 40f) {

                it.distance =
                    260f
            }
        }
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

        particles.forEach {

            val radians =
                Math.toRadians(
                    it.angle.toDouble()
                )

            val x =
                centerX +

                        cos(radians)
                            .toFloat() *
                        it.distance

            val y =
                centerY +

                        sin(radians)
                            .toFloat() *
                        it.distance

            val alpha =
                (
                    255 *

                    (1f -
                     it.distance / 260f)
                    ).toInt()

            paint.color =

                Color.argb(

                    alpha,

                    255,

                    255,

                    255
                )

            canvas.drawCircle(

                x,
                y,

                3f,

                paint
            )
        }
    }
}
