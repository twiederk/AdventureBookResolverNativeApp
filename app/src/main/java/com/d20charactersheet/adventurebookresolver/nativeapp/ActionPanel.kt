package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.KoinComponent
import org.koin.core.inject


class ActionPanel : Panel {

    internal lateinit var actionLabelEditText: EditText
    internal lateinit var actionIdEditText: EditText
    internal lateinit var actionMoveRecyclerView: RecyclerView
    internal var itemTouchHelper: ItemTouchHelper? = null

    override fun create(rootView: View) {
        actionLabelEditText = rootView.findViewById(R.id.action_label_edit_text)
        actionIdEditText = rootView.findViewById(R.id.action_id_edit_text)
        createActionAddButton(rootView)
        createActionMoveRecyclerView(rootView)
    }

    private fun createActionAddButton(rootView: View) {
        rootView.findViewById<Button>(R.id.action_add_button).also {
            it.setOnClickListener(ActionAddOnClickListener())
        }
    }

    private fun createActionMoveRecyclerView(rootView: View) {
        val actionMoveAdapter = ActionMoveAdapter()
        actionMoveRecyclerView = rootView.findViewById<RecyclerView>(R.id.action_move_recycler_view).apply {
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

    fun getActionLabel(): String = actionLabelEditText.text.toString()

    fun getActionId(): String = actionIdEditText.text.toString()

    fun clear() {
        actionLabelEditText.setText("")
        actionIdEditText.setText("")
    }

}

class ActionAddOnClickListener : View.OnClickListener, KoinComponent {

    private val game: Game by inject()
    private val actionPanel: ActionPanel by inject()
    private val bookPanel: BookPanel by inject()

    override fun onClick(v: View?) {
        val actionLabel = actionPanel.getActionLabel()
        val actionId = actionPanel.getActionId()
        addAction(actionLabel, actionId)
    }

    private fun addAction(actionLabel: String, actionId: String) {
        if (isDataValidToCreateAction(actionLabel, actionId)) {
            game.addAction(actionLabel, actionId.toInt())
            actionPanel.clear()
            actionPanel.update()
            bookPanel.update()
        }
    }

    private fun isDataValidToCreateAction(actionLabel: String, actionId: String) =
        actionLabel.isNotEmpty() && actionId.isNotEmpty()

}




