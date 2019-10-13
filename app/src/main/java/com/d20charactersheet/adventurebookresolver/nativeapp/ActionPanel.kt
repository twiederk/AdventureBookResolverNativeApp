package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import org.koin.core.KoinComponent
import org.koin.core.inject

class ActionPanel(private val game: Game) : Panel {

    internal lateinit var actionLabelEditText: EditText
    internal lateinit var actionIdEditText: EditText
    internal lateinit var actionMoveRecyclerView: RecyclerView


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
        actionMoveRecyclerView = rootView.findViewById<RecyclerView>(R.id.action_move_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(rootView.context)
            adapter = ActionMoveAdapter(game)
        }
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

class ActionMoveAdapter(private val game: Game) : RecyclerView.Adapter<ActionMoveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionMoveViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.listitem_move_action, parent, false)
        return ActionMoveViewHolder(rootView)
    }

    override fun onBindViewHolder(actionMoveViewHolder: ActionMoveViewHolder, position: Int) {
        val action: Action = game.book.getActions().elementAt(position)
        actionMoveViewHolder.setAction(action.label, action.destination.id)
    }

    override fun getItemCount() = game.book.getActions().size
}

class ActionMoveViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {

    private val actionMoveLabelTextView: TextView = rootView.findViewById(R.id.move_action_label_text_view)
    private val actionMoveEntryIdButton: Button = rootView.findViewById<Button>(R.id.move_action_entry_id_button).run {
        setOnClickListener(ActionMoveOnClickListener())
        this
    }

    fun setAction(label: String, entryId: Int) {
        actionMoveLabelTextView.text = label
        actionMoveEntryIdButton.text = entryId.toString()
    }
}

class ActionMoveOnClickListener : View.OnClickListener, KoinComponent {

    private val game: Game by inject()
    private val entryPanel: EntryPanel by inject()
    private val actionPanel: ActionPanel by inject()

    override fun onClick(view: View?) {
        val button: Button? = view as? Button
        val entryId = button?.text.toString().toInt()
        game.move(entryId)
        entryPanel.update()
        actionPanel.update()
    }

}



