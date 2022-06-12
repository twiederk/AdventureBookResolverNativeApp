package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookViewModel : KoinComponent, ViewModel() {

    private val game: Game by inject()

    var title by mutableStateOf(game.book.title)
        private set

    fun onSaveClick() {
        game.saveBook()
    }

    fun onTitleChange(title: String) {
        this.title = title
    }

}
