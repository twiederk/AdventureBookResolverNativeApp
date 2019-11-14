package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class LogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.info(javaClass.simpleName + ".onCreate()")
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        Logger.info(javaClass.simpleName + ".onResume()")
        super.onResume()
    }

    override fun onDestroy() {
        Logger.info(javaClass.simpleName + ".onDestroy()")
        super.onDestroy()
    }

}
