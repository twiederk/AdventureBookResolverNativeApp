package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View

// tag::panelInterface[]
interface Panel {
    fun create(rootView: View)
    fun update()
}
// end::panelInterface[]