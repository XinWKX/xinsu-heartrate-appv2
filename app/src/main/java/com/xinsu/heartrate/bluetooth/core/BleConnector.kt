package com.xinsu.heartrate.bluetooth.core

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import com.xinsu.heartrate.core.pulse.PulseEngine

@SuppressLint("MissingPermission")
class BleConnector(

    private val context: Context
) {

    /**
     * 当前 GATT
     */
    private var bluetoothGatt:
            BluetoothGatt? = null

    /**
     * 当前设备
     */
    private var currentDevice:
            BluetoothDevice? = null

    /**
     * 当前是否已连接
     */
    var isConnected =
        false

        private set

    /**
     * BPM 回调
     */
    var onHeartRateChanged:
            ((Int) -> Unit)?
        = null

    /**
     * 状态回调
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
     * 连接 BLE
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

        /**
         * 更新状态
         */
        BleStateManager.setState(
            BleState.CONNECTING
        )

        onConnectionStateChanged?.invoke(
            BleState.CONNECTING
        )

        /**
         * 清理旧 GATT
         */
        try {

            bluetoothGatt?.disconnect()

            bluetoothGatt?.close()

        } catch (_: Exception) {
        }

        bluetoothGatt = null

        /**
         * 超时控制
         */
        BleTimeoutController
            .startTimeout {

                disconnect()
            }

        /**
         * 发起连接
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

        BleStateManager.connectedDeviceName =
            "NONE"

        BleStateManager.currentHeartRate =
            0

        BleStateManager.currentRssi =
            0

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
     * GATT 回调
     */
    private val gattCallback =
        object : BluetoothGattCallback() {

            /**
             * 连接状态变化
             */
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

                        bluetoothGatt = gatt

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
                         * 发现服务
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

                if (
                    status != BluetoothGatt.GATT_SUCCESS
                ) {

                    return
                }

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
                 * 开启通知
                 */
                gatt.setCharacteristicNotification(

                    characteristic,

                    true
                )

                /**
                 * Descriptor
                 */
                val descriptor =

                    characteristic.getDescriptor(

                        BleConstants
                            .CLIENT_CHARACTERISTIC_CONFIG_UUID
                    )

                if (descriptor != null) {

                    descriptor.value =

                        BluetoothGattDescriptor
                            .ENABLE_NOTIFICATION_VALUE

                    gatt.writeDescriptor(
                        descriptor
                    )
                }
            }

            /**
             * 数据变化
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

                /**
                 * 非心率 Characteristic
                 */
                if (

                    characteristic.uuid !=

                    BleConstants
                        .HEART_RATE_CHARACTERISTIC_UUID
                ) {

                    return
                }

                val data =
                    characteristic.value
                        ?: return

                /**
                 * 解析 BPM
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
                 * 更新状态
                 */
                BleStateManager
                    .currentHeartRate = bpm

                onHeartRateChanged?.invoke(
                    bpm
                )

                /**
                 * 刷新 RSSI
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

                if (
                    status != BluetoothGatt.GATT_SUCCESS
                ) {

                    return
                }

                BleStateManager.currentRssi =
                    rssi
            }
        }
}
