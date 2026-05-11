package com.xinsu.heartrate.core.pulse

import kotlin.math.sin

object PulseEngine {

    /**
     * 当前 BPM
     */
    var bpm = 72f

    /**
     * 当前脉冲值
     * 0 ~ 1
     */
    var pulse = 0f
        private set

    /**
     * 时间累计
     */
    private var time = 0f

    /**
     * 更新
     */
    fun update(
        deltaTime: Float
    ) {

        time += deltaTime

        val frequency =
            bpm / 60f

        pulse = (
            (
                sin(
                    time *
                    frequency *
                    Math.PI *
                    2
                ) + 1
            ) / 2f
        ).toFloat()
    }
}
