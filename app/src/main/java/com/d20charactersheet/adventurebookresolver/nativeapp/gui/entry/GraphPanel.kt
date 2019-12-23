package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.view.View
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel


class GraphPanel(internal val bookRenderer: BookRenderer) : Panel {

    internal lateinit var graphView: GraphView

    override fun create(rootView: View) {
        graphView = rootView.findViewById(R.id.graph_view)
    }

    override fun update() {
        val (centerX, centerY) = bookRenderer.center()
        graphView.center(centerX, centerY)
        graphView.invalidate()
    }

}
