package com.xinsu.heartrate.bluetooth.core

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object BlePermissionHelper {

    /**
     * 请求码
     */
    const val REQUEST_CODE_BLE = 1001

    /**
     * Android 12+
     */
    private val android12Permissions =

        arrayOf(

            Manifest.permission.BLUETOOTH_SCAN,

            Manifest.permission.BLUETOOTH_CONNECT
        )

    /**
     * Android 10 / 11
     */
    private val legacyPermissions =

        arrayOf(

            Manifest.permission.ACCESS_FINE_LOCATION
        )

    /**
     * 检查权限
     */
    fun hasPermissions(
        context: Context
    ): Boolean {

        val permissions =

            if (

                Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.S

            ) {

                android12Permissions

            } else {

                legacyPermissions
            }

        permissions.forEach {

            val granted =

                ContextCompat
                    .checkSelfPermission(

                        context,

                        it
                    )

            if (
                granted !=
                PackageManager.PERMISSION_GRANTED
            ) {

                return false
            }
        }

        return true
    }

    /**
     * 请求权限
     */
    fun requestPermissions(
        activity: Activity
    ) {

        val permissions =

            if (

                Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.S

            ) {

                android12Permissions

            } else {

                legacyPermissions
            }

        ActivityCompat.requestPermissions(

            activity,

            permissions,

            REQUEST_CODE_BLE
        )
    }

    /**
     * 权限结果
     */
    fun verifyGrantResults(

        grantResults: IntArray
    ): Boolean {

        if (grantResults.isEmpty()) {

            return false
        }

        grantResults.forEach {

            if (
                it !=
                PackageManager.PERMISSION_GRANTED
            ) {

                return false
            }
        }

        return true
    }
}
