@file:Suppress("TooManyFunctions")

package com.example.injectiontest.component.log

import android.util.Log.ASSERT
import android.util.Log.DEBUG
import android.util.Log.ERROR
import android.util.Log.INFO
import android.util.Log.VERBOSE
import android.util.Log.WARN
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Logging Interface user to allow access of a Logger from an API module without needing to depend on the
 * [:features:loggingApi] API module.
 */
interface PlayerLog {

    /**
     * Log the specified [message] with optional [throwable] at the specified [priority], if logging at that level with
     * that priority has been enabled.
     */
    fun logMessage(priority: Int, throwable: Throwable? = null, message: () -> String)
}

/**
 * Log [VERBOSE] logs if enabled for this tag.
 */
fun PlayerLog.v(throwable: Throwable? = null, message: () -> String) = logMessage(VERBOSE, throwable, message)

/**
 * Log [DEBUG] logs if enabled for this tag.
 */
fun PlayerLog.d(throwable: Throwable? = null, message: () -> String) = logMessage(DEBUG, throwable, message)

/**
 * Log [INFO] logs if enabled for this tag.
 */
fun PlayerLog.i(throwable: Throwable? = null, message: () -> String) = logMessage(INFO, throwable, message)

/**
 * Log [WARN] logs if enabled for this tag.
 */
fun PlayerLog.w(throwable: Throwable? = null, message: () -> String) = logMessage(WARN, throwable, message)

/**
 * Log [ERROR] logs if enabled for this tag.
 */
fun PlayerLog.e(throwable: Throwable? = null, message: () -> String) = logMessage(ERROR, throwable, message)

/**
 * Log [ASSERT] logs if enabled for this tag.
 */
fun PlayerLog.wtf(throwable: Throwable? = null, message: () -> String) = logMessage(ASSERT, throwable, message)

/**
 * Evaluate and log the [message] if it is enabled for the given [tag] and [priority] for each value that this
 * [Flowable] emits.
 */
inline fun <T> Flowable<T>.logOnNext(
    tag: PlayerLog,
    priority: Int = DEBUG,
    crossinline message: (T) -> String
): Flowable<T> = doOnNext { tag.logMessage(priority) { message.invoke(it) } }

/**
 * Evaluate and log the [message] if it is enabled for the given [tag] and [priority] for each value that this
 * [Observable] emits.
 */
inline fun <T> Observable<T>.logOnNext(
    tag: PlayerLog,
    priority: Int = DEBUG,
    crossinline message: (T) -> String
): Observable<T> = doOnNext { tag.logMessage(priority) { message.invoke(it) } }

/**
 * Evaluate and log the [message] if it is enabled for the given [tag] and [priority] for each value that this
 * [Single] emits.
 */
inline fun <T> Single<T>.logOnSuccess(
    tag: PlayerLog,
    priority: Int = DEBUG,
    crossinline message: (T) -> String
): Single<T> = doOnSuccess { tag.logMessage(priority) { message.invoke(it) } }

/**
 * Evaluate and log the [message] if it is enabled for the given [tag] and [priority] for each value that this
 * [Single] emits.
 */
inline fun <T> Single<T>.logOnError(
    tag: PlayerLog,
    priority: Int = ERROR,
    crossinline message: (Throwable) -> String
): Single<T> = doOnError { tag.logMessage(priority, it) { message.invoke(it) } }

/**
 * Evaluate and log the [message] if it is enabled for the given [tag] and [priority] for each value that this
 * [Observable] emits.
 */
inline fun <T> Flowable<T>.logOnError(
    tag: PlayerLog,
    priority: Int = ERROR,
    crossinline message: (Throwable) -> String
): Flowable<T> = doOnError { tag.logMessage(priority, it) { message.invoke(it) } }
