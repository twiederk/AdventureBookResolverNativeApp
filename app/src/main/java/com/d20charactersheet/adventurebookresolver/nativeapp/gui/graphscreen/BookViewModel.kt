package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

class BookViewModel(val game: Game) : ViewModel() {

    var title by mutableStateOf(this.game.book.title)
        private set

    fun onSaveClick() {
        this.game.saveBook()
    }

    fun onTitleChange(title: String) {
        this.title = title
    }

}
