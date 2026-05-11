package com.xinsu.heartrate.particles.engine

import com.xinsu.heartrate.core.pulse.PulseEngine
import com.xinsu.heartrate.particles.model.Particle
import com.xinsu.heartrate.settings.core.SettingsManager
import com.xinsu.heartrate.transition.engine.TransitionEngine
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class ParticleEngine {

    private val particles =
        mutableListOf<Particle>()

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

    fun update(

        deltaTime: Float,

        width: Int,

        height: Int
    ) {

        val pulseScale =
            1f +
            PulseEngine.pulse * 0.08f

        val collapse =
            1f -
            TransitionEngine.progress
                * 0.92f

        particles.forEach {

            it.angle +=
                it.speed *
                deltaTime *
                60f

            val radians =
                Math.toRadians(
                    it.angle.toDouble()
                )

            val orbit =
                it.orbitRadius *
                pulseScale *
                collapse

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

    fun getParticles():
            List<Particle> {

        return particles
    }
}
