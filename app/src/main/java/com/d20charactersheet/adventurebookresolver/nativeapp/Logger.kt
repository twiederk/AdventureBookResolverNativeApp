package com.d20charactersheet.adventurebookresolver.nativeapp

import android.util.Log

object Logger {

    private const val LOG_TAG = "abr"

    fun debug(message: String) {
        Log.d(LOG_TAG, message)
    }

    fun info(message: String) {
        Log.i(LOG_TAG, message)
    }

}
