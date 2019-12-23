package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel
import org.koin.core.KoinComponent
import org.koin.core.inject

class EntryPanel(private val game: Game) : Panel {

    internal lateinit var entryTitleEditText: EditText
    internal lateinit var entryIdTextView: TextView
    internal lateinit var entryNoteEditText: EditText

    override fun create(rootView: View) {
        entryTitleEditText = rootView.findViewById(R.id.entry_title_edit_text)
        entryTitleEditText.onFocusChangeListener =
            EntryOnFocusChangeListener { text ->
                game.book.setEntryTitle(text)
            }
        entryIdTextView = rootView.findViewById(R.id.entry_id_text_view)
        entryNoteEditText = rootView.findViewById(R.id.entry_note_edit_text)
        entryNoteEditText.onFocusChangeListener =
            EntryOnFocusChangeListener { text ->
                game.book.setEntryNote(text)
            }
    }

    override fun update() {
        entryTitleEditText.setText(game.book.getEntryTitle())
        entryIdTextView.text = displayEntryId()
        entryNoteEditText.setText(game.book.getEntryNote())
    }

    private fun displayEntryId(): String = "(${game.book.getEntryId()})"
}

class EntryOnFocusChangeListener(private val gameFunction: (text: String) -> Unit) :
    View.OnFocusChangeListener, KoinComponent {

    private val graphPanel: GraphPanel by inject()

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        if (!hasFocus) {
            val editText = view as EditText
            gameFunction(editText.text.toString())
            graphPanel.update()
        }
    }

}

