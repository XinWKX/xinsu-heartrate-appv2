package com.xinsu.heartrate.bluetooth.core

object HeartRateParser {

    /**
     * 解析 BLE 心率数据
     *
     * Heart Rate Measurement
     * UUID: 0x2A37
     */
    fun parse(
        data: ByteArray
    ): Int {

        if (data.isEmpty()) {

            return 0
        }

        /**
         * Flags
         */
        val flags =
            data[0].toInt()

        /**
         * bit0:
         *
         * 0 -> UINT8
         * 1 -> UINT16
         */
        val isUInt16 =
            flags and 0x01 != 0

        return if (isUInt16) {

            /**
             * UINT16
             */
            if (data.size < 3) {

                0

            } else {

                (
                    (data[2].toInt() and 0xFF)
                            shl 8
                        ) or
                        (
                            data[1].toInt()
                                    and 0xFF
                            )
            }

        } else {

            /**
             * UINT8
             */
            if (data.size < 2) {

                0

            } else {

                data[1].toInt() and 0xFF
            }
        }
    }
}
