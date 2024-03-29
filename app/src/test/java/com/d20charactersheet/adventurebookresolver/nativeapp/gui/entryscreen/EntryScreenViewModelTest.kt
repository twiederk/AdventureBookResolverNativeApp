package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen

import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class EntryScreenViewModelTest {

    private val game: Game = mock()
    private val entryScreenViewModel = EntryScreenViewModel(game)

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `should init with default values`() {
        // assert
        assertThat(entryScreenViewModel.id).isZero
        assertThat(entryScreenViewModel.title).isEmpty()
        assertThat(entryScreenViewModel.note).isEmpty()
        assertThat(entryScreenViewModel.visit).isEqualTo(Visit.UNVISITED)
        assertThat(entryScreenViewModel.wayMark).isEqualTo(WayMark.NORMAL)
        assertThat(entryScreenViewModel.actions).isEmpty()
    }

    @Test
    fun `should init entry screen view model with given book entry`() {
        // arrange
        val bookEntry = BookEntry(
            id = 1,
            title = "myTitle",
            note = "myNote",
            visit = Visit.VISITED,
            wayMark = WayMark.DEAD_END
        )
        val action = Action(
            label = "myLabel",
            source = BookEntry(1),
            destination = BookEntry(2)
        )
        whenever(game.getActions(bookEntry)).doReturn(listOf(action))

        // act
        entryScreenViewModel.initBookEntry(bookEntry)

        // assert
        assertThat(entryScreenViewModel.id).isEqualTo(1)
        assertThat(entryScreenViewModel.title).isEqualTo("myTitle")
        assertThat(entryScreenViewModel.note).isEqualTo("myNote")
        assertThat(entryScreenViewModel.visit).isEqualTo(Visit.VISITED)
        assertThat(entryScreenViewModel.wayMark).isEqualTo(WayMark.DEAD_END)
        assertThat(entryScreenViewModel.actions).containsExactly(action)
    }

    @Test
    fun `should set title in view model and book entry`() {
        // arrange
        val bookEntry = BookEntry(1)
        entryScreenViewModel.initBookEntry(bookEntry)

        // act
        entryScreenViewModel.onTitleChanged("myTitle")

        // assert
        assertThat(entryScreenViewModel.title).isEqualTo("myTitle")
        assertThat(bookEntry.title).isEqualTo("myTitle")
    }

    @Test
    fun `should set note in view model and book entry`() {
        // arrange
        val bookEntry = BookEntry(1)
        entryScreenViewModel.initBookEntry(bookEntry)

        // act
        entryScreenViewModel.onNoteChanged("myNote")

        // assert
        assertThat(entryScreenViewModel.note).isEqualTo("myNote")
        assertThat(bookEntry.note).isEqualTo("myNote")
    }

    @Test
    fun `should set way mark in view model and book entry`() {
        // arrange
        val bookEntry = BookEntry(1)
        entryScreenViewModel.initBookEntry(bookEntry)

        // act
        entryScreenViewModel.onWayMarkSelected(WayMark.WAY_POINT)

        // assert
        assertThat(entryScreenViewModel.wayMark).isEqualTo(WayMark.WAY_POINT)
        assertThat(bookEntry.wayMark).isEqualTo(WayMark.WAY_POINT)
    }

    @Test
    fun `should move to book entry`() {
        // act
        entryScreenViewModel.onActionMoveClicked(10)

        // assert
        verify(game).move(10)
    }

    @Test
    fun `should delete action and update actions`() {

        // act
        entryScreenViewModel.onActionDeleteClicked(5)

        // assert
        inOrder(game) {
            verify(game).delete(5)
            verify(game).getActions(any())
        }
    }

    @Test
    fun `should run to this entry`() {
        // arrange
        val bookEntry = BookEntry(
            id = 10,
            title = "myTitle",
            note = "myNote",
            visit = Visit.VISITED,
            wayMark = WayMark.DEAD_END
        )
        entryScreenViewModel.initBookEntry(bookEntry)

        // act
        entryScreenViewModel.onRunClick()

        // assert
        verify(game).runTo(10)
    }

}