package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

class GraphScreenViewModel(private val game: Game) : ViewModel() {

    var scale by mutableStateOf(1F)
        private set

    val title: String
        get() = game.book.title

    fun onSaveClick() {
        this.game.saveBook()
    }

    fun onScaleChange(scale: Float) {
        this.scale = scale
    }

    fun export() {
        game.exportBook()
    }

}
