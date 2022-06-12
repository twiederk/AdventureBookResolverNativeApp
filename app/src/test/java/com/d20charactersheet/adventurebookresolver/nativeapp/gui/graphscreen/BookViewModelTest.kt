package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class BookViewModelTest : KoinTest {

    private val game: Game by inject()
    private val bookViewModel: BookViewModel by inject()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<Game>()
        whenever(game.book).thenReturn(AdventureBook())
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun should_return_title_of_book() {

        // act
        val title = bookViewModel.title

        // assert
        Assertions.assertThat(title).isEqualTo("new book")
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
        Assertions.assertThat(bookViewModel.title).isEqualTo("new title")
    }

}