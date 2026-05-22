package com.xinsu.heartrate.bluetooth.parser

import android.bluetooth.BluetoothGattCharacteristic

object HeartRateParser {

    fun parse(
        characteristic:
        BluetoothGattCharacteristic
    ): Int {

        val flag =
            characteristic.properties

        val format = if (
            flag and 0x01 != 0
        ) {

            BluetoothGattCharacteristic
                .FORMAT_UINT16

        } else {

            BluetoothGattCharacteristic
                .FORMAT_UINT8
        }

        return characteristic
            .getIntValue(
                format,
                1
            ) ?: 0
    }
}
