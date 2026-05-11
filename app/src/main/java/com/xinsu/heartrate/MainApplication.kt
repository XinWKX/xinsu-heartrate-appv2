package com.xinsu.heartrate

import android.app.Application
import com.xinsu.heartrate.crash.core.CrashHandler

class MainApplication :
    Application() {

    override fun onCreate() {

        super.onCreate()

        // 全局崩溃捕获
        Thread.setDefaultUncaughtExceptionHandler(

            CrashHandler(this)
        )
    }
    }
