package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.Test

class AbstractBookRendererTest {

    @Test
    fun sortEntriesByDepth_sortTwoEntries_sortedEntries() {
        // arrange
        val game = Game()
        game.book = AdventureBook().apply {
            setEntryTitle("Entry Hall")
            addAction("to throne", 2)
        }

        // act
        val entriesByDepth = TraversalBookRenderer(game).sortEntriesByDepth(game.book.graph, game.book.getBookEntry(1))

        // assert
        assertThat(entriesByDepth).contains(
            entry(0, mutableListOf(BookEntry(1))),
            entry(1, mutableListOf(BookEntry(2))),
        )
    }

}