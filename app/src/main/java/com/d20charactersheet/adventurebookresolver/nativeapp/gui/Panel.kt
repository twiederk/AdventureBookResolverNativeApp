package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import android.view.View

// tag::panelInterface[]
interface Panel {
    fun create(rootView: View)
    fun update()
}
// end::panelInterface[]