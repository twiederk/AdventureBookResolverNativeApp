package com.d20charactersheet.adventurebookresolver.nativeapp

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView

class EntryPanel(private val game: Game) : Panel {

    private lateinit var entryTitleEditText: EditText
    private lateinit var entryIdTextView: TextView
    private lateinit var entryNoteEditText: EditText

    override fun create(rootView: View) {
        entryTitleEditText = rootView.findViewById(R.id.entry_title_edit_text)
        entryTitleEditText.addTextChangedListener(GameTextWatcher { text -> game.book.editBookEntry(text) })
        entryIdTextView = rootView.findViewById(R.id.entry_id_text_view)
        entryNoteEditText = rootView.findViewById(R.id.entry_note_edit_text)
        entryNoteEditText.addTextChangedListener(GameTextWatcher { text -> game.book.note(text) })
        update()
    }

    override fun update() {
        entryTitleEditText.setText(game.book.getEntryTitle())
        entryIdTextView.text = displayEntryId()
        entryNoteEditText.setText(game.book.getEntryNote())
    }

    private fun displayEntryId(): String = "(${game.book.getEntryId()})"
}

class GameTextWatcher(private val gameFunction: (text: String) -> Unit) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(editable: Editable?) {
        gameFunction(editable.toString())
    }

}

