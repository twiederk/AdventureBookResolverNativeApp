package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.KoinComponent
import org.koin.core.inject

class EntryDialog : KoinComponent {

    private val game: Game by inject()

    internal lateinit var entryTitleEditText: EditText
    internal lateinit var entryNoteEditText: EditText

    internal var itemTouchHelper: ItemTouchHelper? = null
    private lateinit var actionMoveRecyclerView: RecyclerView

    private var dialog: AlertDialog? = null


    fun show(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_entry, null)
        onCreate(view)

        dialog = AlertDialog.Builder(context)
            .setView(view)
            .setPositiveButton("OK") { _, _ -> updateEntry() }
            .setNegativeButton("CANCEL") { dialog, _ -> dialog.cancel() }
            .create()
        dialog?.show()
    }

    internal fun onCreate(view: View) {
        createEntryTextFields(view)
        createActionMoveRecyclerView(view)
    }

    private fun createEntryTextFields(view: View) {
        entryTitleEditText = view.findViewById(R.id.entry_title_edit_text)
        entryTitleEditText.setText(game.book.getEntryTitle())
        val entryIdTextView: TextView = view.findViewById(R.id.entry_id_text_view)
        entryIdTextView.text = displayEntryId()
        entryNoteEditText = view.findViewById(R.id.entry_note_edit_text)
        entryNoteEditText.setText(game.book.getEntryNote())
    }

    private fun displayEntryId(): String = "(${game.book.getEntryId()})"


    internal fun updateEntry() {
        game.book.setEntryTitle(entryTitleEditText.text.toString())
        game.book.setEntryNote(entryNoteEditText.text.toString())
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

    fun close() {
        dialog?.cancel()
    }

}
