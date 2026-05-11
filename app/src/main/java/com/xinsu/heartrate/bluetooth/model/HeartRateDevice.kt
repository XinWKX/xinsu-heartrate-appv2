package com.xinsu.heartrate.bluetooth.model

data class HeartRateDevice(

    val name: String,

    val address: String,

    val rssi: Int,

    val connected: Boolean = false
)
