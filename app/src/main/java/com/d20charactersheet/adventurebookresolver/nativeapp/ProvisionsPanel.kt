package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.Button
import android.widget.TextView

class ProvisionsPanel(private val game: Game) : Panel {

    internal lateinit var provisionsValueTextView: TextView

    override fun create(rootView: View) {
        provisionsValueTextView = rootView.findViewById(R.id.provisions_value_text_view)
        rootView.findViewById<Button>(R.id.provisions_plus_button)
            .setOnClickListener(ProvisionsIncreaseOnClickListener())
        rootView.findViewById<Button>(R.id.provisions_minus_button)
            .setOnClickListener(ProvisionsDecreaseOnClickListener())
        rootView.findViewById<Button>(R.id.provisions_eat_button)
            .setOnClickListener(ProvisionsEatOnClickListener())
    }

    override fun update() {
        provisionsValueTextView.text = game.getProvisions()
    }

}
