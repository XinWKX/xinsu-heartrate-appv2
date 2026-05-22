package com.xinsu.heartrate.bluetooth.core

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import com.xinsu.heartrate.core.pulse.PulseEngine

@SuppressLint("MissingPermission")
class BleConnector(

    private val context: Context
) {

    private var bluetoothGatt:
            BluetoothGatt? = null

    private var currentDevice:
            BluetoothDevice? = null

    /**
     * 当前连接状态
     */
    var isConnected =
        false

        private set

    /**
     * 心率变化回调
     */
    var onHeartRateChanged:
            ((Int) -> Unit)?
        = null

    /**
     * 连接状态变化
     */
    var onConnectionStateChanged:
            ((BleState) -> Unit)?
        = null

    init {

        /**
         * 自动重连
         */
        BleReconnectEngine
            .setReconnectAction {

                currentDevice?.let {

                    connect(it)
                }
            }
    }

    /**
     * 连接设备
     */
    fun connect(
        device: BluetoothDevice
    ) {

        /**
         * 防止重复连接
         */
        if (
            !BleConnectionLock.canConnect()
        ) {

            return
        }

        BleConnectionLock.lock()

        currentDevice = device

        isConnected = false

        BleStateManager.setState(
            BleState.CONNECTING
        )

        onConnectionStateChanged?.invoke(
            BleState.CONNECTING
        )

        /**
         * 清理旧连接
         */
        try {

            bluetoothGatt?.disconnect()

            bluetoothGatt?.close()

        } catch (_: Exception) {
        }

        bluetoothGatt = null

        /**
         * 连接超时
         */
        BleTimeoutController
            .startTimeout {

                disconnect()
            }

        /**
         * 发起 GATT
         */
        bluetoothGatt =

            device.connectGatt(

                context,

                false,

                gattCallback
            )
    }

    /**
     * 主动断开
     */
    fun disconnect() {

        try {

            bluetoothGatt?.disconnect()

            bluetoothGatt?.close()

        } catch (_: Exception) {
        }

        bluetoothGatt = null

        isConnected = false

        BleTimeoutController.cancel()

        BleConnectionLock.unlock()

        BleStateManager.setState(
            BleState.DISCONNECTED
        )

        onConnectionStateChanged?.invoke(
            BleState.DISCONNECTED
        )
    }

    /**
     * 是否已连接
     */
    fun isConnected(): Boolean {

        return isConnected
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

                super.onConnectionStateChange(

                    gatt,

                    status,

                    newState
                )

                when (newState) {

                    /**
                     * 已连接
                     */
                    BluetoothProfile
                        .STATE_CONNECTED -> {

                        isConnected = true

                        BleTimeoutController
                            .cancel()

                        BleConnectionLock
                            .unlock()

                        BleStateManager
                            .setState(

                                BleState.CONNECTED
                            )

                        onConnectionStateChanged?.invoke(
                            BleState.CONNECTED
                        )

                        BleStateManager
                            .connectedDeviceName =

                            gatt.device.name
                                ?: "UNKNOWN"

                        /**
                         * 开始发现服务
                         */
                        gatt.discoverServices()
                    }

                    /**
                     * 已断开
                     */
                    BluetoothProfile
                        .STATE_DISCONNECTED -> {

                        isConnected = false

                        BleConnectionLock
                            .unlock()

                        BleStateManager
                            .setState(

                                BleState.DISCONNECTED
                            )

                        onConnectionStateChanged?.invoke(
                            BleState.DISCONNECTED
                        )

                        /**
                         * 自动重连
                         */
                        BleReconnectEngine
                            .attemptReconnect()
                    }

                    /**
                     * 正在连接
                     */
                    BluetoothProfile
                        .STATE_CONNECTING -> {

                        BleStateManager
                            .setState(

                                BleState.CONNECTING
                            )

                        onConnectionStateChanged?.invoke(
                            BleState.CONNECTING
                        )
                    }
                }
            }

            /**
             * 服务发现
             */
            override fun onServicesDiscovered(

                gatt: BluetoothGatt,

                status: Int
            ) {

                super.onServicesDiscovered(
                    gatt,
                    status
                )

                val service =

                    gatt.getService(

                        BleConstants
                            .HEART_RATE_SERVICE_UUID
                    )

                        ?: return

                val characteristic =

                    service.getCharacteristic(

                        BleConstants
                            .HEART_RATE_CHARACTERISTIC_UUID
                    )

                        ?: return

                /**
                 * 开启 Notify
                 */
                gatt.setCharacteristicNotification(

                    characteristic,

                    true
                )

                /**
                 * CCCD Descriptor
                 */
                val descriptor =

                    characteristic.getDescriptor(

                        BleConstants
                            .CLIENT_CHARACTERISTIC_CONFIG_UUID
                    )

                descriptor?.let {

                    it.value =

                        BluetoothGattDescriptor
                            .ENABLE_NOTIFICATION_VALUE

                    gatt.writeDescriptor(it)
                }
            }

            /**
             * 心率数据变化
             */
            override fun onCharacteristicChanged(

                gatt: BluetoothGatt,

                characteristic:
                BluetoothGattCharacteristic
            ) {

                super.onCharacteristicChanged(

                    gatt,

                    characteristic
                )

                val data =
                    characteristic.value
                        ?: return

                /**
                 * BPM 解析
                 */
                val bpm =
                    HeartRateParser.parse(
                        data
                    )

                /**
                 * 更新 Pulse
                 */
                PulseEngine.bpm =
                    bpm.toFloat()

                /**
                 * 更新状态层
                 */
                BleStateManager
                    .currentHeartRate = bpm

                onHeartRateChanged?.invoke(
                    bpm
                )

                /**
                 * 更新 RSSI
                 */
                bluetoothGatt
                    ?.readRemoteRssi()
            }

            /**
             * RSSI
             */
            override fun onReadRemoteRssi(

                gatt: BluetoothGatt,

                rssi: Int,

                status: Int
            ) {

                super.onReadRemoteRssi(

                    gatt,

                    rssi,

                    status
                )

                BleStateManager.currentRssi =
                    rssi
            }
        }
}
