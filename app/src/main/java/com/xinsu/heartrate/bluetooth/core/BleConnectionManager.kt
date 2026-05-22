package com.xinsu.heartrate.bluetooth.core

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import com.xinsu.heartrate.bluetooth.parser.HeartRateParser
import java.util.UUID

class BleConnectionManager(

    private val context: Context
) {

    companion object {

        val HEART_RATE_SERVICE_UUID:

        UUID = UUID.fromString(

            "0000180D-0000-1000-8000-00805F9B34FB"
        )

        val HEART_RATE_CHARACTERISTIC_UUID:

        UUID = UUID.fromString(

            "00002A37-0000-1000-8000-00805F9B34FB"
        )
    }

    private var bluetoothGatt:
            BluetoothGatt? = null

    var onHeartRateChanged:
            ((Int) -> Unit)? = null

    var onConnected:
            (() -> Unit)? = null

    var onDisconnected:
            (() -> Unit)? = null

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

    @SuppressLint("MissingPermission")
    fun disconnect() {

        bluetoothGatt?.disconnect()

        bluetoothGatt?.close()

        bluetoothGatt = null
    }

    private val gattCallback =

        object : BluetoothGattCallback() {

            override fun onConnectionStateChange(

                gatt: BluetoothGatt,

                status: Int,

                newState: Int
            ) {

                when (newState) {

                    BluetoothProfile.STATE_CONNECTED -> {

                        onConnected?.invoke()

                        gatt.discoverServices()
                    }

                    BluetoothProfile.STATE_DISCONNECTED -> {

                        onDisconnected?.invoke()
                    }
                }
            }

            override fun onServicesDiscovered(

                gatt: BluetoothGatt,

                status: Int
            ) {

                val service =
                    gatt.getService(
                        HEART_RATE_SERVICE_UUID
                    ) ?: return

                val characteristic =
                    service.getCharacteristic(
                        HEART_RATE_CHARACTERISTIC_UUID
                    ) ?: return

                enableNotification(
                    gatt,
                    characteristic
                )
            }

            override fun onCharacteristicChanged(

                gatt: BluetoothGatt,

                characteristic:
                BluetoothGattCharacteristic
            ) {

                val bpm =
                    HeartRateParser.parse(
                        characteristic
                    )

                onHeartRateChanged
                    ?.invoke(bpm)
            }
        }

    @SuppressLint("MissingPermission")
    private fun enableNotification(

        gatt: BluetoothGatt,

        characteristic:
        BluetoothGattCharacteristic
    ) {

        gatt.setCharacteristicNotification(

            characteristic,

            true
        )

        val descriptor =
            characteristic.getDescriptor(

                UUID.fromString(

                    "00002902-0000-1000-8000-00805f9b34fb"
                )
            )

        descriptor.value =
            BluetoothGattDescriptor
                .ENABLE_NOTIFICATION_VALUE

        gatt.writeDescriptor(
            descriptor
        )
    }
}
