package com.xinsu.heartrate.bluetooth.core

object BleConnectionLock {

    private var locked = false

    /**
     * 是否允许连接
     */
    fun canConnect():

            Boolean {

        return !locked
    }

    /**
     * 上锁
     */
    fun lock() {

        locked = true
    }

    /**
     * 解锁
     */
    fun unlock() {

        locked = false
    }
}
