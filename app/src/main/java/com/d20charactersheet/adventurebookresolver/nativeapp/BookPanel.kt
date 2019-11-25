package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.EditText
import android.widget.TextView

class BookPanel(private val game: Game) : Panel {

    internal lateinit var triesValueTextView: TextView
    internal lateinit var entriesValueTextView: TextView
    internal lateinit var bookNoteEditText: EditText

    override fun create(rootView: View) {
        triesValueTextView = rootView.findViewById(R.id.tries_value_text_view)
        entriesValueTextView = rootView.findViewById(R.id.entries_value_text_view)
        bookNoteEditText = rootView.findViewById(R.id.book_note_edit_text)
        bookNoteEditText.onFocusChangeListener = GameOnFocusChangeListener { text -> game.book.note = text }
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
