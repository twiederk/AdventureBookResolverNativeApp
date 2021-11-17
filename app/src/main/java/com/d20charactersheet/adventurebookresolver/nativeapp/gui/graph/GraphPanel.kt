package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.View
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel


class GraphPanel(internal val bookRenderer: TraversalBookRenderer) : Panel {

    internal lateinit var graphView: GraphView

    override fun create(rootView: View) {
        graphView = rootView.findViewById(R.id.graph_view)
    }

    override fun update() {
        graphView.renderMode = RenderMode.CENTER
        graphView.invalidate()
    }

    fun scale(scale: Float) {
        graphView.scale(scale)
    }

}
