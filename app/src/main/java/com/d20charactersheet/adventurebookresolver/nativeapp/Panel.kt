package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View

interface Panel {
    fun update()
    fun create(rootView: View)
}