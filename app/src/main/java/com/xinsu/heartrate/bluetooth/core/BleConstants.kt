package com.xinsu.heartrate.bluetooth.core

import java.util.UUID

object BleConstants {

    /**
     * Heart Rate Service
     */
    val HEART_RATE_SERVICE_UUID:

            UUID = UUID.fromString(

        "0000180D-0000-1000-8000-00805F9B34FB"
    )

    /**
     * Heart Rate Measurement
     */
    val HEART_RATE_CHARACTERISTIC_UUID:

            UUID = UUID.fromString(

        "00002A37-0000-1000-8000-00805F9B34FB"
    )
}
