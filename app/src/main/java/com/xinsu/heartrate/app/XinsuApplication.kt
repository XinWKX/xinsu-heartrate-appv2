package com.xinsu.heartrate.app

import android.app.Application
import com.xinsu.heartrate.crash.handler.CrashHandler

class XinsuApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        initCrashHandler()
    }

    /**
     * 初始化全局崩溃捕获
     */
    private fun initCrashHandler() {

        Thread.setDefaultUncaughtExceptionHandler(

            CrashHandler(this)
        )
    }
}
