package com.xinsu.heartrate.bluetooth.core

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import com.xinsu.heartrate.core.pulse.PulseEngine

class BleConnector(

    private val context: Context
) {

    private var bluetoothGatt:
            BluetoothGatt? = null

    /**
     * 连接设备
     */
    @SuppressLint("MissingPermission")
    fun connect(
        device: BluetoothDevice
    ) {

        bluetoothGatt =

            device.connectGatt(

                context,

                false,

                gattCallback
            )
    }

    /**
     * GATT 回调
     */
    private val gattCallback =
        object : BluetoothGattCallback() {

            override fun onConnectionStateChange(

                gatt: BluetoothGatt,

                status: Int,

                newState: Int
            ) {

                if (

                    newState ==
                    BluetoothProfile.STATE_CONNECTED

                ) {

                    gatt.discoverServices()
                }
            }

            override fun onServicesDiscovered(

                gatt: BluetoothGatt,

                status: Int
            ) {

                val service =

                    gatt.getService(

                        BleConstants
                            .HEART_RATE_SERVICE_UUID
                    )

                val characteristic =

                    service?.getCharacteristic(

                        BleConstants
                            .HEART_RATE_CHARACTERISTIC_UUID
                    )

                if (characteristic != null) {

                    gatt.setCharacteristicNotification(

                        characteristic,

                        true
                    )
                }
            }

            override fun onCharacteristicChanged(

                gatt: BluetoothGatt,

                characteristic:
                BluetoothGattCharacteristic
            ) {

                val data =
                    characteristic.value
                        ?: return

                val bpm =
                    HeartRateParser.parse(
                        data
                    )

                PulseEngine.bpm =
                    bpm.toFloat()
            }
        }
}
