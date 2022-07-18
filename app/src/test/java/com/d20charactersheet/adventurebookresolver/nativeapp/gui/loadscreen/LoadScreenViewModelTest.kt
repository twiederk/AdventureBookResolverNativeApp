package com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.BookStore
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.FileHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LoadScreenViewModelTest {

    private val game: Game = mock()
    private val fileHelper: FileHelper = mock()
    private val bookStore: BookStore = mock()
    private val loadScreenViewModel = LoadScreenViewModel(game, fileHelper, bookStore)

    @Test
    fun `should return list of file names`() {
        // assert
        whenever(fileHelper.getInternalFileNames()).thenReturn(listOf("file 1", "file 2"))

        // act
        val fileNames = LoadScreenViewModel(game, fileHelper, bookStore).fileNames

        // assert
        assertThat(fileNames).containsExactly("file 1", "file 2")
    }

    @Test
    fun `should load book from file`() {
        // arrange
        val loadedBook = AdventureBook()
        whenever(fileHelper.loadFile("first book.abr")).thenReturn(emptyList())
        whenever(bookStore.import(emptyList())).thenReturn(loadedBook)

        // act
        loadScreenViewModel.loadBook("first book.abr")

        // assert
        verify(game).book = loadedBook
    }

    @Test
    fun `should delete file`() {

        // act
        loadScreenViewModel.deleteBook("first book.abr")

        // assert
        verify(fileHelper).deleteFile("first book.abr")
    }

}