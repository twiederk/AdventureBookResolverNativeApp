package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class BookViewModelTest {

    private val game: Game = mock()
    private lateinit var bookViewModel: BookViewModel

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun before() {
        whenever(game.book).thenReturn(AdventureBook())
        bookViewModel = BookViewModel(game)
    }

    @Test
    fun should_return_title_of_book() {

        // act
        val title = bookViewModel.title

        // assert
        assertThat(title).isEqualTo("new book")
    }

    @Test
    fun should_save_book() {

        // act
        bookViewModel.onSaveClick()

        // assert
        verify(game).saveBook()
    }

    @Test
    fun should_change_book_title() {

        // act
        bookViewModel.onTitleChange("new title")

        // assert
        assertThat(bookViewModel.title).isEqualTo("new title")
    }

}