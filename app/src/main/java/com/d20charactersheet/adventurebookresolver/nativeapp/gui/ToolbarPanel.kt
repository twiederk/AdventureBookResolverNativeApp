package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

class ToolbarPanel(private val game: Game) : Panel {

    private lateinit var toolbar: Toolbar

    override fun create(rootView: View) {
        toolbar = rootView.findViewById(R.id.toolbar)
    }

    override fun update() {
        toolbar.title = game.book.title
    }

    fun getToolbar(): Toolbar = toolbar
}