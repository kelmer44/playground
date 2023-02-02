package com.example.injectiontest.component.log

import timber.log.Timber

/**
 * Logging object that can be injected into Core Player and Player Feature modules.
 *
 * To set the log level, run the following ADB command:
 *
 * ```
 * adb shell setprop log.tag.DmgzPlayerLogImpl VERBOSE
 * ```
 */
object PlayerLogImpl: PlayerLog {
    override fun logMessage(priority: Int, throwable: Throwable?, message: () -> String) =
       Timber.Forest.log(priority, throwable, message())
}
