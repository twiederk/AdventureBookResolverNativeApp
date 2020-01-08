package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ActionPanel : Panel {

    internal lateinit var actionMoveRecyclerView: RecyclerView
    internal var itemTouchHelper: ItemTouchHelper? = null

    override fun create(rootView: View) {
        createActionAddFloatingActionButton(rootView)
        createActionMoveRecyclerView(rootView)
    }

    private fun createActionAddFloatingActionButton(rootView: View) {
        rootView.findViewById<FloatingActionButton>(R.id.action_add_floating_action_button).apply {
            setOnClickListener(FloatingActionButtonOnClickListener())
        }
    }

    private fun createActionMoveRecyclerView(rootView: View) {
        val actionMoveAdapter = ActionMoveAdapter()
        actionMoveRecyclerView =
            rootView.findViewById<RecyclerView>(R.id.action_move_recycler_view).apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(rootView.context)
                adapter = actionMoveAdapter
            }
        if (itemTouchHelper == null) {
            itemTouchHelper = ItemTouchHelper(ActionDeleteOnSwipeListener(actionMoveAdapter))
        }
        itemTouchHelper?.attachToRecyclerView(actionMoveRecyclerView)
    }

    override fun update() {
        actionMoveRecyclerView.adapter?.notifyDataSetChanged()
    }

}

class FloatingActionButtonOnClickListener(private val actionAddDialog: ActionAddDialog = ActionAddDialog()) :
    View.OnClickListener {

    override fun onClick(view: View) {
        actionAddDialog.show(view.context)
    }

}




