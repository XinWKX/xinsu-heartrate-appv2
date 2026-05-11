package com.xinsu.heartrate.crash.logger

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CrashLogger {

    fun saveCrashLog(

        context: Context,

        throwable: Throwable
    ) {

        try {

            val time = SimpleDateFormat(

                "yyyy-MM-dd_HH-mm-ss",

                Locale.getDefault()
            ).format(Date())

            val crashDir = File(

                context.filesDir,

                "crash_logs"
            )

            if (!crashDir.exists()) {

                crashDir.mkdirs()
            }

            val crashFile = File(

                crashDir,

                "crash_$time.txt"
            )

            crashFile.writeText(

                throwable.stackTraceToString()
            )

        } catch (_: Exception) {

        }
    }
}
