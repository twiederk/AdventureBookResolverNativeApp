package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

class CreateBookScreenViewModel(val game: Game) : ViewModel() {

    var title by mutableStateOf("")
        private set

    fun onTitleChange(title: String) {
        this.title = title
    }

    fun onCreateClick() {
        this.game.createBook(title)
    }


}
