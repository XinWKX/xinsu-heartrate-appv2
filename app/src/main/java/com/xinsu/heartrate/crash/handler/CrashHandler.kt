package com.xinsu.heartrate.crash.handler

import android.content.Context
import android.content.Intent
import android.util.Log
import com.xinsu.heartrate.crash.logger.CrashLogger
import com.xinsu.heartrate.crash.ui.CrashActivity
import kotlin.system.exitProcess

class CrashHandler(

    private val context: Context

) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(

        thread: Thread,

        throwable: Throwable

    ) {

        CrashLogger.saveCrashLog(

            context,

            throwable
        )

        val crashLog = Log.getStackTraceString(
            throwable
        )

        val intent = Intent(

            context,

            CrashActivity::class.java
        )

        intent.putExtra(

            "crash_log",

            crashLog
        )

        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
        )

        context.startActivity(intent)

        android.os.Process.killProcess(
            android.os.Process.myPid()
        )

        exitProcess(1)
    }
}
