package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel

class ProvisionsPanel(private val game: Game) :
    Panel {

    internal lateinit var provisionsValueTextView: TextView
    internal lateinit var provisionsEatButton: Button

    override fun create(rootView: View) {
        provisionsValueTextView = rootView.findViewById(R.id.provisions_value_text_view)
        rootView.findViewById<Button>(R.id.provisions_plus_button)
            .setOnClickListener(ProvisionsIncreaseOnClickListener())
        rootView.findViewById<Button>(R.id.provisions_minus_button)
            .setOnClickListener(ProvisionsDecreaseOnClickListener())
        provisionsEatButton = rootView.findViewById(R.id.provisions_eat_button)
        provisionsEatButton.setOnClickListener(ProvisionsEatOnClickListener())
    }

    override fun update() {
        provisionsValueTextView.text = game.getProvisions()
        provisionsEatButton.isEnabled = game.hasProvisions()
    }

}
