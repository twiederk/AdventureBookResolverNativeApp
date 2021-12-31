package com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel

class BookPanel(private val game: Game) :
    Panel {

    internal lateinit var triesValueTextView: TextView
    internal lateinit var entriesValueTextView: TextView
    internal lateinit var bookNoteEditText: EditText

    override fun create(rootView: View) {
        triesValueTextView = rootView.findViewById(R.id.tries_value_text_view)
        entriesValueTextView = rootView.findViewById(R.id.entries_value_text_view)
        bookNoteEditText = rootView.findViewById(R.id.book_note_edit_text)
        bookNoteEditText.addTextChangedListener(NoteTextWatcher(game))
    }

    override fun update() {
        triesValueTextView.text = game.book.tries.toString()
        entriesValueTextView.text =
            displayBookEntries(game.book.getAllBookEntries().size, game.book.totalNumberOfBookEntries)
        bookNoteEditText.setText(game.book.note)
    }

    private fun displayBookEntries(numberOfBookEntries: Int, totalNumberOfBookEntries: Int): String {
        val percentage = "%.0f".format(numberOfBookEntries / totalNumberOfBookEntries.toFloat() * 100)
        return "$numberOfBookEntries / $totalNumberOfBookEntries ($percentage%)"
    }

}

class NoteTextWatcher(private val game: Game) : TextWatcher {
    override fun afterTextChanged(s: Editable) {}

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        game.book.note = s.toString()
    }

}
