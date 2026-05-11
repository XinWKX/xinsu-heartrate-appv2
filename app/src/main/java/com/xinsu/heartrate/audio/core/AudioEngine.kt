package com.xinsu.heartrate.audio.core

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.xinsu.heartrate.R

object AudioEngine {

    private var soundPool:
            SoundPool? = null

    private var pulseSound = 0

    private var loaded = false

    /**
     * 初始化
     */
    fun initialize(
        context: Context
    ) {

        if (soundPool != null) {

            return
        }

        soundPool = SoundPool.Builder()

            .setMaxStreams(4)

            .setAudioAttributes(

                AudioAttributes.Builder()

                    .setUsage(
                        AudioAttributes.USAGE_MEDIA
                    )

                    .setContentType(
                        AudioAttributes.CONTENT_TYPE_SONIFICATION
                    )

                    .build()
            )

            .build()

        pulseSound = soundPool!!.load(

            context,

            R.raw.pulse_tick,

            1
        )

        soundPool!!.setOnLoadCompleteListener {

                _, _, status ->

            loaded = status == 0
        }
    }

    /**
     * 播放 Pulse Tick
     */
    fun playPulse() {

        if (!loaded) {

            return
        }

        soundPool?.play(

            pulseSound,

            0.18f,

            0.18f,

            1,

            0,

            1f
        )
    }

    /**
     * 释放
     */
    fun release() {

        soundPool?.release()

        soundPool = null
    }
}
