package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.TextView

class BookPanel(private val game: Game) : Panel {

    internal lateinit var triesValueTextView: TextView
    internal lateinit var entriesValueTextView: TextView

    override fun create(rootView: View) {
        triesValueTextView = rootView.findViewById(R.id.tries_value_text_view)
        entriesValueTextView = rootView.findViewById(R.id.entries_value_text_view)
    }

    override fun update() {
        triesValueTextView.text = game.book.tries.toString()
        entriesValueTextView.text =
            displayBookEntries(game.book.getAllBookEntries().size, game.book.totalNumberOfBookEntries)
    }

    private fun displayBookEntries(numberOfBookEntries: Int, totalNumberOfBookEntries: Int): String {
        val percentage = "%.0f".format(numberOfBookEntries / totalNumberOfBookEntries.toFloat() * 100)
        return "$numberOfBookEntries / $totalNumberOfBookEntries ($percentage%)"
    }

}
