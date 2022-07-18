package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GraphScreenViewModelTest {

    private val game: Game = mock()
    private lateinit var graphScreenViewModel: GraphScreenViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        whenever(game.book).thenReturn(AdventureBook())
        graphScreenViewModel = GraphScreenViewModel(game)
    }

    @Test
    fun should_update_scale_when_changed() {

        // act
        graphScreenViewModel.onScaleChange(2F)

        // assert
        assertThat(graphScreenViewModel.scale).isEqualTo(2F)
    }

    @Test
    fun should_return_title_of_book() {

        // act
        val title = graphScreenViewModel.title

        // assert
        assertThat(title).isEqualTo("new book")
    }

    @Test
    fun should_save_book() {

        // act
        graphScreenViewModel.onSaveClick()

        // assert
        verify(game).saveBook()
    }

    @Test
    fun should_export_book() {

        // act
        graphScreenViewModel.export()

        // assert
        verify(game).exportBook(any())
    }

}