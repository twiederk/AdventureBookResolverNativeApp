package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.LogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class GraphFragment : LogFragment() {

    private val graphPanel: GraphPanel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_graph, container, false)
        createSaveFloatingActionButton(rootView)
        createActionAddFloatingActionButton(rootView)
        graphPanel.create(rootView)

        setHasOptionsMenu(true)

        return rootView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.option_zoom_10 -> {
                scale(0.1f)
                true
            }
            R.id.option_zoom_25 -> {
                scale(0.25f)
                true
            }
            R.id.option_zoom_50 -> {
                scale(0.5f)
                true
            }
            R.id.option_zoom_100 -> {
                scale(1f)
                true
            }
            R.id.option_zoom_200 -> {
                scale(2f)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    private fun scale(scale: Float) {
        graphPanel.scale(scale)
    }


    override fun onResume() {
        super.onResume()
        graphPanel.update()
    }

    private fun createActionAddFloatingActionButton(rootView: View) {
        rootView.findViewById<FloatingActionButton>(R.id.action_add_floating_action_button).apply {
            setOnClickListener(AddActionOnClickListener())
        }
    }

    private fun createSaveFloatingActionButton(rootView: View) {
        rootView.findViewById<FloatingActionButton>(R.id.save_floating_action_button).apply {
            setOnClickListener(SaveOnClickListener())
        }
    }

}

class AddActionOnClickListener(private val actionAddDialog: ActionAddDialog = ActionAddDialog()) :
    View.OnClickListener {

    override fun onClick(view: View) {
        actionAddDialog.show(view.context)
    }

}

class SaveOnClickListener : KoinComponent, View.OnClickListener {

    private val game: Game by inject()

    override fun onClick(view: View) {
        game.saveBook()
    }

}


