package com.xinsu.heartrate.bluetooth.core

object HeartRateParser {

    /**
     * 解析 BPM
     */
    fun parse(
        data: ByteArray
    ): Int {

        if (data.isEmpty()) {

            return 0
        }

        val flag =
            data[0].toInt()

        val format =
            flag and 0x01

        return if (format == 0) {

            data[1].toInt() and 0xFF

        } else {

            (
                (data[2].toInt() shl 8)
                        or
                        (data[1].toInt() and 0xFF)
                )
        }
    }
}
