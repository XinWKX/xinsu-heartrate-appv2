package com.xinsu.heartrate.bluetooth.model

data class HeartRateDevice(

    val name: String,

    val mac: String,

    val rssi: Int,

    val hasHeartRateService:
    Boolean = true
)
