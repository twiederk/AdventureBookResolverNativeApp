package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.LogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject

open class GraphFragment : LogFragment() {

    private val graphPanel: GraphPanel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_graph, container, false)
        createActionAddFloatingActionButton(rootView)
        graphPanel.create(rootView)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        graphPanel.update()
    }

    private fun createActionAddFloatingActionButton(rootView: View) {
        rootView.findViewById<FloatingActionButton>(R.id.action_add_floating_action_button).apply {
            setOnClickListener(FloatingActionButtonOnClickListener())
        }
    }

}

class FloatingActionButtonOnClickListener(private val actionAddDialog: ActionAddDialog = ActionAddDialog()) :
    View.OnClickListener {

    override fun onClick(view: View) {
        actionAddDialog.show(view.context)
    }

}


