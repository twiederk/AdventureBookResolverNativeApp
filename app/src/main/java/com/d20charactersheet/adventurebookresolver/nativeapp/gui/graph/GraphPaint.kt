package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.graphics.Color
import android.graphics.Paint

object GraphPaint {

    val entryPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    val currentEntryPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    val edgePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 10f
    }

    var textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 72f
        color = Color.RED
    }

    fun getScaledTextPaint(scale: Float): Paint = Paint(textPaint)
        .apply {
            textSize *= scale
        }

}