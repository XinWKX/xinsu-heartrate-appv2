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

    private var currentDevice:
            BluetoothDevice? = null

    init {

        BleReconnectEngine
            .setReconnectAction {

                currentDevice?.let {

                    connect(it)
                }
            }
    }

    /**
     * 连接
     */
    @SuppressLint("MissingPermission")
    fun connect(
        device: BluetoothDevice
    ) {

        if (
            !BleConnectionLock.canConnect()
        ) {

            return
        }

        BleConnectionLock.lock()

        currentDevice = device

        BleStateManager.setState(
            BleState.CONNECTING
        )

        bluetoothGatt?.close()

        BleTimeoutController
            .startTimeout {

                disconnect()
            }

        bluetoothGatt =

            device.connectGatt(

                context,

                false,

                gattCallback
            )
    }

    /**
     * 断开
     */
    @SuppressLint("MissingPermission")
    fun disconnect() {

        bluetoothGatt?.disconnect()

        bluetoothGatt?.close()

        bluetoothGatt = null

        BleConnectionLock.unlock()

        BleStateManager.setState(
            BleState.DISCONNECTED
        )
    }

    /**
     * GATT Callback
     */
    private val gattCallback =
        object : BluetoothGattCallback() {

            override fun onConnectionStateChange(

                gatt: BluetoothGatt,

                status: Int,

                newState: Int
            ) {

                when (newState) {

                    BluetoothProfile
                        .STATE_CONNECTED -> {

                        BleTimeoutController
                            .cancel()

                        BleConnectionLock
                            .unlock()

                        BleStateManager
                            .setState(

                                BleState.CONNECTED
                            )

                        BleStateManager
                            .connectedDeviceName =

                            gatt.device.name
                                ?: "UNKNOWN"

                        gatt.discoverServices()
                    }

                    BluetoothProfile
                        .STATE_DISCONNECTED -> {

                        BleConnectionLock
                            .unlock()

                        BleStateManager
                            .setState(

                                BleState.DISCONNECTED
                            )

                        BleReconnectEngine
                            .attemptReconnect()
                    }
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

            override fun onReadRemoteRssi(

                gatt: BluetoothGatt,

                rssi: Int,

                status: Int
            ) {

                BleStateManager.currentRssi =
                    rssi
            }
        }
}
