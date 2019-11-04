package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.core.domain.Action
import org.koin.core.KoinComponent
import org.koin.core.inject

class ActionMoveAdapter : RecyclerView.Adapter<ActionMoveViewHolder>(), KoinComponent {

    private val game: Game by inject()
    private val bookPanel: BookPanel by inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionMoveViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.listitem_move_action, parent, false)
        return ActionMoveViewHolder(rootView)
    }

    override fun onBindViewHolder(actionMoveViewHolder: ActionMoveViewHolder, position: Int) {
        val action: Action = game.getAction(position)
        actionMoveViewHolder.setAction(action.label, action.destination.id)
    }

    override fun getItemCount() = game.getNumberOfActions()

    fun deleteItem(position: Int) {
        val action = game.getAction(position)
        game.delete(action.destination.id)
        bookPanel.update()
    }
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

class ActionDeleteOnSwipeListener(private val actionMoveAdapter: ActionMoveAdapter) : OnSwipeListener() {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        actionMoveAdapter.deleteItem(viewHolder.adapterPosition)
        actionMoveAdapter.notifyItemRemoved(viewHolder.adapterPosition)
    }

}
