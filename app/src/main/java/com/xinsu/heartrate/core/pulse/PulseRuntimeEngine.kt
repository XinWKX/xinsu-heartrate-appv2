package com.xinsu.heartrate.core.pulse

import com.xinsu.heartrate.bluetooth.data.HeartRateRepository
import kotlin.math.sin

object PulseRuntimeEngine {

    var pulse = 0f

    private var time = 0f

    fun update() {

        val bpm =
            HeartRateRepository.currentBpm

        if (bpm <= 0) {

            pulse = 0f

            return
        }

        val frequency =
            bpm / 60f

        time += 0.016f

        pulse = (

            sin(
                time *
                frequency *
                Math.PI *
                2
            ) * 0.5f + 0.5f

        ).toFloat()
    }
}
