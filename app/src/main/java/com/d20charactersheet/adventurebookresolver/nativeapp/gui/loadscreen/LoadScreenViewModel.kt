package com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.d20charactersheet.adventurebookresolver.core.domain.BookStore
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.FileHelper

class LoadScreenViewModel(
    private val game: Game,
    private val fileHelper: FileHelper = FileHelper(null),
    private val bookStore: BookStore = BookStore()
) : ViewModel() {

    var fileNames by mutableStateOf(fileHelper.getInternalFileNames())
        private set

    fun loadBook(fileName: String) {
        val content: List<String> = fileHelper.loadFile(fileName)
        game.book = bookStore.import(content)
    }

    fun deleteBook(fileName: String) {
        fileHelper.deleteFile(fileName)
        fileNames = fileHelper.getInternalFileNames()
    }

}
