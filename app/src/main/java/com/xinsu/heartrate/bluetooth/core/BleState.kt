package com.xinsu.heartrate.bluetooth.core

enum class BleState {

    /**
     * 空闲
     */
    IDLE,

    /**
     * 扫描中
     */
    SCANNING,

    /**
     * 正在连接
     */
    CONNECTING,

    /**
     * 已连接
     */
    CONNECTED,

    /**
     * 已断开
     */
    DISCONNECTED,

    /**
     * 连接失败
     */
    FAILED
}
