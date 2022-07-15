package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CreateBookScreenViewModelTest {

    private val game: Game = mock()
    private val createBookScreenViewModel = CreateBookScreenViewModel(game)

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `should init with default values`() {
        // assert
        assertThat(createBookScreenViewModel.title).isEmpty()
    }

    @Test
    fun `should update title when title is entered`() {

        // act
        createBookScreenViewModel.onTitleChange("myTitle")

        // assert
        assertThat(createBookScreenViewModel.title).isEqualTo("myTitle")
    }

    @Test
    fun `should create new book with given title`() {
        // arrange
        createBookScreenViewModel.onTitleChange("myTitle")

        // act
        createBookScreenViewModel.onCreateClick()

        // assert
        verify(game).createBook("myTitle")
    }

}