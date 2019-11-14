package com.d20charactersheet.adventurebookresolver.nativeapp

import android.util.Log

object Logger {

    private const val LOG_TAG = "abr"

    fun debug(message: String) {
        Log.d(LOG_TAG, message)
    }

    fun debug(message: String, exception: Exception) {
        Log.d(LOG_TAG, message, exception)
    }

    fun info(message: String) {
        Log.i(LOG_TAG, message)
    }

    fun info(message: String, exception: Exception) {
        Log.i(LOG_TAG, message, exception)
    }

    fun warn(message: String) {
        Log.w(LOG_TAG, message)
    }

    fun warn(message: String, exception: Exception) {
        Log.w(LOG_TAG, message, exception)
    }

    fun error(message: String) {
        Log.e(LOG_TAG, message)
    }

    fun error(message: String, exception: Exception) {
        Log.e(LOG_TAG, message, exception)
    }

}
