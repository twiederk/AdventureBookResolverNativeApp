package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel

class GoldPanel(private val game: Game) :
    Panel {

    internal lateinit var goldValueTextView: TextView

    override fun create(rootView: View) {
        goldValueTextView = rootView.findViewById(R.id.gold_value_text_view)
        rootView.findViewById<Button>(R.id.gold_plus_button).setOnClickListener(GoldIncreaseOnClickListener())
        rootView.findViewById<Button>(R.id.gold_minus_button).setOnClickListener(GoldDecreaseOnClickListener())
    }

    override fun update() {
        goldValueTextView.text = game.getGold()
    }

}
