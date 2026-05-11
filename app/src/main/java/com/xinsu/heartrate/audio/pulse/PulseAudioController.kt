package com.xinsu.heartrate.audio.pulse

import com.xinsu.heartrate.audio.core.AudioEngine
import com.xinsu.heartrate.core.pulse.PulseEngine

class PulseAudioController {

    private var lastPulse = 0f

    /**
     * 更新
     */
    fun update() {

        val current =
            PulseEngine.pulse

        // 上升沿触发
        if (

            current > 0.92f &&

            lastPulse <= 0.92f
        ) {

            AudioEngine.playPulse()
        }

        lastPulse = current
    }
}
