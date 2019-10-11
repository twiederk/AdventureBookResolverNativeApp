package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View

// tag::panelInterface[]
interface Panel {
    fun update()
    fun create(rootView: View)
}
// end::panelInterface[]