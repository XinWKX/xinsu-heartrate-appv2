package com.xinsu.heartrate.particles.engine

import com.xinsu.heartrate.core.pulse.PulseEngine
import com.xinsu.heartrate.particles.model.Particle
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class ParticleEngine {

    private val particles =
        mutableListOf<Particle>()

    /**
     * 初始化粒子
     */
    fun initialize(

        width: Int,

        height: Int
    ) {

        particles.clear()

        repeat(120) {

            val depth =
                Random.nextFloat()

            particles.add(

                Particle(

                    x = width / 2f,

                    y = height / 2f,

                    radius =
                        2f + depth * 6f,

                    alpha =
                        (20 + depth * 80).toInt(),

                    depth = depth,

                    angle =
                        Random.nextFloat() * 360f,

                    orbitRadius =
                        80f + depth * 400f,

                    speed =
                        0.02f + depth * 0.08f
                )
            )
        }
    }

    /**
     * 更新粒子
     */
    fun update(
        deltaTime: Float,

        width: Int,

        height: Int
    ) {

        val pulseScale =
            1f + PulseEngine.pulse * 0.08f

        particles.forEach {

            it.angle +=
                it.speed * deltaTime * 60f

            val radians =
                Math.toRadians(
                    it.angle.toDouble()
                )

            val orbit =
                it.orbitRadius * pulseScale

            it.x = (

                width / 2f +

                cos(radians)
                    .toFloat() * orbit

            )

            it.y = (

                height / 2f +

                sin(radians)
                    .toFloat() * orbit

            )
        }
    }

    /**
     * 获取粒子
     */
    fun getParticles():
            List<Particle> {

        return particles
    }
}
