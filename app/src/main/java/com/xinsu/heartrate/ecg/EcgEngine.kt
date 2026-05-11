package com.xinsu.heartrate.ecg

import com.xinsu.heartrate.core.pulse.PulseEngine
import kotlin.math.sin

object EcgEngine {

    private var time = 0f

    /**
     * 更新
     */
    fun update(
        deltaTime: Float
    ) {

        time += deltaTime * 4f

        val bpm =
            PulseEngine.bpm

        val pulseSpeed =
            bpm / 60f

        val wave = generateWave(
            time,
            pulseSpeed
        )

        EcgDataBuffer.add(

            EcgPoint(

                x = time,

                y = wave
            )
        )
    }

    /**
     * ECG波形
     */
    private fun generateWave(

        t: Float,

        speed: Float

    ): Float {

        val base =
            sin(t * 2f) * 0.04f

        val pulse =
            kotlin.math.exp(

                -((t * speed % 2f) - 0.2f)
                        .let { it * it } * 60f

            ) * 1.6f

        return base + pulse
    }
}
