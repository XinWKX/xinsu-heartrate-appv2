package com.xinsu.heartrate.particles.engine

import com.xinsu.heartrate.core.pulse.PulseRuntimeEngine
import com.xinsu.heartrate.particles.model.Particle
import com.xinsu.heartrate.settings.core.SettingsManager
import com.xinsu.heartrate.transition.engine.TransitionEngine
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

        repeat(
            SettingsManager.particleCount
        ) {

            val depth =
                Random.nextFloat()

            particles.add(

                Particle(

                    x = width / 2f,

                    y = height / 2f,

                    radius =
                        2f + depth * 8f,

                    alpha =
                        (15 + depth * 90)
                            .toInt(),

                    depth = depth,

                    angle =
                        Random.nextFloat() * 360f,

                    orbitRadius =
                        100f + depth * 500f,

                    speed =
                        0.015f +
                        depth * 0.05f
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

            1f +

            PulseRuntimeEngine.pulse
                * 0.08f

        val collapse =

            1f -

            TransitionEngine.progress
                * 0.92f

        particles.forEach {

            particle ->

            particle.angle +=

                particle.speed *

                deltaTime *

                60f

            val radians =

                Math.toRadians(

                    particle.angle.toDouble()
                )

            val orbit =

                particle.orbitRadius *

                pulseScale *

                collapse

            particle.x = (

                width / 2f +

                cos(radians)
                    .toFloat() * orbit
            )

            particle.y = (

                height / 2f +

                sin(radians)
                    .toFloat() * orbit
            )
        }
    }

    /**
     * 获取粒子列表
     */
    fun getParticles():
            List<Particle> {

        return particles
    }
}
