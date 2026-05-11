package com.xinsu.heartrate.ecg

object EcgDataBuffer {

    private val points =
        mutableListOf<EcgPoint>()

    /**
     * 添加点
     */
    fun add(
        point: EcgPoint
    ) {

        points.add(point)

        if (points.size > 240) {

            points.removeAt(0)
        }
    }

    /**
     * 获取
     */
    fun get():

            List<EcgPoint> {

        return points
    }

    /**
     * 清空
     */
    fun clear() {

        points.clear()
    }
}
