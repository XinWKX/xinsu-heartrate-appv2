package com.xinsu.heartrate.ecg.engine

import com.xinsu.heartrate.core.pulse.PulseEngine
import com.xinsu.heartrate.ecg.model.EcgPoint
import kotlin.math.sin

class EcgEngine {

    private val points =
        mutableListOf<EcgPoint>()

    private var time = 0f

    /**
     * 初始化
     */
    fun initialize(
        width: Int
    ) {

        points.clear()

        for (i in 0 until width step 8) {

            points.add(

                EcgPoint(

                    i.toFloat(),

                    0f
                )
            )
        }
    }

    /**
     * 更新波形
     */
    fun update(
        deltaTime: Float
    ) {

        time += deltaTime * 4f

        val pulse =
            PulseEngine.pulse

        points.forEachIndexed {

                index,
                point ->

            val phase =
                (
                    index * 0.18f
                ) + time

            val wave =
                sin(phase) * 12f

            val heartbeat =
                generateHeartbeat(
                    phase,
                    pulse
                )

            point.y =
                wave.toFloat() +
                heartbeat
        }
    }

    /**
     * 心跳冲击
     */
    private fun generateHeartbeat(

        phase: Float,

        pulse: Float

    ): Float {

        val shock =
            sin(phase * 2.5f)

        return if (

            shock > 0.985f

        ) {

            120f * pulse

        } else {

            0f
        }
    }

    /**
     * 获取点
     */
    fun getPoints():
            List<EcgPoint> {

        return points
    }
}
